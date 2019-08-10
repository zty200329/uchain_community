package com.swpu.uchain.community.controller;

import com.swpu.uchain.community.dto.AddTimeDTO;
import com.swpu.uchain.community.dto.SetQualifyTimeDTO;
import com.swpu.uchain.community.service.AddTimeService;
import com.swpu.uchain.community.service.SignInService;
import com.swpu.uchain.community.service.UserService;
import com.swpu.uchain.community.util.DateUtil;
import com.swpu.uchain.community.util.ResultVOUtil;
import com.swpu.uchain.community.vo.ResultVO;
import com.swpu.uchain.community.vo.SomeOneTimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author；lzh
 * @Date:2019/8/820:12 Descirption:
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SignInService signInService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddTimeService addTimeService;

    /**
     * 管理员获取所有人一周的打卡情况
     * @return
     */
    @GetMapping("/getWeekInfo")
    public ResultVO getAllWeekInfo() {
        String startDate = DateUtil.getMonDay();
        String endDate = DateUtil.getSunDay();
        return signInService.getAllTimeByDates(startDate,endDate);
    }

    /**
     * 管理员查看某人在指定时间的打卡情况
     * @param userName
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/getOneTime")
    public ResultVO getOneTime(String userName, String startDate, String endDate) {
        SomeOneTimeVo someOneTimeVo = signInService.getOneTime(startDate, endDate, userName);
        return ResultVOUtil.success(someOneTimeVo);
    }

    /**
     * 管理员动态设置队员一周打卡时间
     * @param setQualifyTimeDTO
     * @return
     */
    @PostMapping("/setQualifyTime")
    public ResultVO setQualifyTime(SetQualifyTimeDTO setQualifyTimeDTO) {
        return userService.setQualifyTime(setQualifyTimeDTO.getUserName(), setQualifyTimeDTO.getQualifyTime());
    }

    /**
     * 管理员对补签申请进行审核
     * @param addTimeDTO
     * @return
     */
    @PostMapping("/checkApplication")
    public ResultVO check(AddTimeDTO addTimeDTO) {
        return addTimeService.check(addTimeDTO);
    }

    /**
     * 管理员获取本周所有的补签申请
     * @return
     */
    @GetMapping("/getAllAdd")
    public ResultVO getAllAdd() {
        return addTimeService.getAllAddInfo();
    }

    /**
     * 管理员获取指定一段时间所有人的打卡时间
     */
    @GetMapping("getAllTime")
    public ResultVO getAllTime(String startDate, String endDate) {
        return signInService.getAllTimeByDates(startDate, endDate);
    }
}
