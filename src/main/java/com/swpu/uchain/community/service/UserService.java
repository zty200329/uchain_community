package com.swpu.uchain.community.service;

import com.swpu.uchain.community.entity.User;
import com.swpu.uchain.community.form.LoginForm;
import com.swpu.uchain.community.vo.ResultVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author hobo
 * @description
 */
public interface UserService {

    /**
     * 根据学号获取用户
     * @param stuId
     * @return
     */
    User getUserByStuId(String stuId);

    /**
     * 从token中解析用户
     * @return
     */
    User getCurrentUser();

    ResultVO login(LoginForm loginForm, HttpServletResponse response);
}
