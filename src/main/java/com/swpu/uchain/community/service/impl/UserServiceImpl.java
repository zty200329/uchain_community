package com.swpu.uchain.community.service.impl;

import com.swpu.uchain.community.dao.UserMapper;
import com.swpu.uchain.community.dto.SetQualifyTimeDTO;
import com.swpu.uchain.community.entity.User;
import com.swpu.uchain.community.enums.ResultEnum;
import com.swpu.uchain.community.exception.BasicException;
import com.swpu.uchain.community.form.LoginForm;
import com.swpu.uchain.community.security.JwtProperties;
import com.swpu.uchain.community.security.JwtUserDetailServiceImpl;
import com.swpu.uchain.community.service.UserService;
import com.swpu.uchain.community.util.JwtTokenUtil;
import com.swpu.uchain.community.util.ResultVOUtil;
import com.swpu.uchain.community.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hobo
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUserDetailServiceImpl jwtUserDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public User getUserByStuId(String stuId) {
        return userMapper.getUserByStuId(stuId);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String stuId = authentication.getName();
        String key = "anonymousUser";
        if (!stuId.equals(key)) {
            return getUserByStuId(stuId);
        }
        return null;
    }

    @Override
    public ResultVO login(LoginForm loginForm, HttpServletResponse response) {
        User user = userMapper.getUserByStuId(loginForm.getStuId());
        if (user == null) {
            return ResultVOUtil.error(ResultEnum.USER_NOT_EXIST);
        }
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(loginForm.getStuId());
        if (!(new BCryptPasswordEncoder().matches(loginForm.getPassword(), userDetails.getPassword()))) {
            return ResultVOUtil.error(ResultEnum.PASSWORD_ERROR);
        }
        Authentication token = new UsernamePasswordAuthenticationToken(loginForm.getStuId(), loginForm.getPassword(), userDetails.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String realToken = jwtTokenUtil.generateToken(userDetails);
        response.addHeader(jwtProperties.getTokenName(), realToken);


        Map map = new HashMap();
        map.put("role", user.getRole());
        map.put("token", realToken);

        return ResultVOUtil.success(map);
    }

    @Override
    public ResultVO setQualifyTime(String userName, Integer qualifyTime) {
        int result = userMapper.updateQualifyTime(userName, qualifyTime);
        if(result != 1){
            throw new BasicException(ResultEnum.USER_NOT_EXIST.getCode(), ResultEnum.USER_NOT_EXIST.getMsg());
        }
        SetQualifyTimeDTO setQualifyTimeDTO = new SetQualifyTimeDTO(userName, qualifyTime);
        return ResultVOUtil.success(setQualifyTimeDTO);
    }
}
