package com.swpu.uchain.community.service;

import com.swpu.uchain.community.vo.ResultVO;
import com.swpu.uchain.community.vo.SomeOneTimeVo;

/**
 * @author；lzh
 * @Date:2019/7/1715:12 Descirption:
 */
public interface SignInService {
    /**
     * 用户签到
     * @param userId
     * @return
     */
    ResultVO userSignIn(String userId);

    /**
     * 用户签退
     * @param userId
     * @return
     */
    ResultVO userSignOut(String userId);

    /**
     * 得到个人的签到信息列表
     * @param startDate
     * @param endDate
     * @return
     */
    ResultVO getSelfTimeInfo(String startDate, String endDate);

    /**
     * 签到页面  获取签到情况列表
     * @param tempDate
     * @return
     */
    ResultVO getSignList(String tempDate);

    /**
     * 管理员通过日期  获得期间之内各成员的签到情况
     * @param startDate
     * @param endDate
     * @return
     */
    ResultVO getAllTimeByDates(String startDate, String endDate);

    /**
     * 查询 某人在某周内的签到情况
     * @param startDate
     * @param endDate
     * @param userName
     * @return
     */
    SomeOneTimeVo getOneTime(String startDate, String endDate, String userName);

    /**
     * 得到任意时间段的打卡记录
     * @param startDate
     * @param endDate
     * @return
     */
    ResultVO getAnyTime(String startDate, String endDate);

    /**
     * 用户查询自己任意时间的记录
     * @param startDate
     * @param endDate
     * @return
     */
    ResultVO getSelfAnyTime(String startDate, String endDate);
}
