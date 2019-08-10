package com.swpu.uchain.community.service.impl;

import com.swpu.uchain.community.dao.AddInfoMapper;
import com.swpu.uchain.community.dao.TimeInfoMapper;
import com.swpu.uchain.community.dao.UserMapper;
import com.swpu.uchain.community.entity.AddInfo;
import com.swpu.uchain.community.entity.TimeInfo;
import com.swpu.uchain.community.entity.User;
import com.swpu.uchain.community.enums.BackMessageEnum;
import com.swpu.uchain.community.enums.ResultEnum;
import com.swpu.uchain.community.exception.BasicException;
import com.swpu.uchain.community.service.SignInService;
import com.swpu.uchain.community.util.DateUtil;
import com.swpu.uchain.community.util.KeyUtil;
import com.swpu.uchain.community.util.ResultVOUtil;
import com.swpu.uchain.community.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author；lzh
 * @Date:2019/7/1715:15 Descirption:
 */
@Service
@Slf4j
public class SignInServiceImpl implements SignInService {

    /**
     * 为0时，签到可用
     */
    private final int SIGN_IN = 0;
    /**
     * 为1时，签到中，签退可用
     */
    private final int SIGN_OUT = 1;
    /**
     * 签到记录有效
     */
    private final String VALID = "Y";
    /**
     * 签到记录无效
     */
    private final String UNVALID = "N";
    /**
     * 该条补签记录还未审核
     */
    private final Integer SUPP_NOT_CHECK = new Integer(0);
    /**
     * 该条记录为补签且通过
     */
    private final Integer SUPP = new Integer(1);
    /**
     * 该条补签拒绝
     */
    private final Integer SUPP_NOT_PASS = new Integer(2);
    /**
     * 每周应签到时长
     */
    private final int PRESCRIBEDTIME = 35;
    /**
     * 大一每周签到时间
     */
    private final int FRASHMAN = 28;

    private final int OTHER_FRESHMAN = 20;
    /**
     * 允许的平均签到时长
     */
    private final int AVERAGETIMEHOUR = 8;

    @Autowired
    private TimeInfoMapper timeInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddInfoMapper addInfoMapper;

    @Override
    public synchronized ResultVO userSignIn(String userId) {
        String timeDate = DateUtil.getTimeDate();
        List<TimeInfo> timeInfoList = timeInfoMapper.isRepeat(userId, timeDate);
        if(timeInfoList.size() == 0){
            String timeId = KeyUtil.getUniqueKey();
            String timeIn = DateUtil.getTimeInfo();
            /** 签到之后，签退方可使用*/
            int result = timeInfoMapper.addTimeTable(timeId, userId, timeDate, timeIn, SIGN_OUT);
            if(result != 1){
                throw new BasicException(ResultEnum.SQL_ERROR.getCode(),ResultEnum.SQL_ERROR.getMsg());
            }
            return ResultVOUtil.success(timeId);
        }else {
            return ResultVOUtil.error(ResultEnum.SIGNIN_ERROR);
        }
    }

    @Override
    public synchronized ResultVO userSignOut(String userId) {
        ResultVO resultVo = ResultVOUtil.error(ResultEnum.SIGNOUT_ERROR);
        String tempDate = DateUtil.getTimeDate();
        List<TimeInfo> timeInfoList = timeInfoMapper.isRepeat(userId, tempDate);
        for(TimeInfo timeInfo: timeInfoList) {
            /** 判断签到签退是否在同一天，否则视为签退超时*/
            if(!tempDate.equals(timeInfo.getTimeDate())) {
                throw new BasicException(ResultEnum.SIGNIN_OUT.getCode(),ResultEnum.SIGNIN_OUT.getMsg());
            }
            String timeOut = DateUtil.getTimeInfo();
            String signDiff = DateUtil.calculateTimeLen(timeInfo.getTimeIn(), timeOut);
            String status = "";
            if(!"0-0".equals(signDiff)) {
                log.info("[签退]:有效签退");
                status = VALID;
            } else {
                log.info("[签退]：无效签退");
                status = UNVALID;
            }
            /** 签退之后，签到可用*/
            int result = timeInfoMapper.updateTimeTable(timeInfo.getTimeId(), timeOut ,SIGN_IN, status);
            if(result != 1){
                throw new BasicException(ResultEnum.SQL_ERROR.getCode(),ResultEnum.SQL_ERROR.getMsg());
            }
            resultVo = ResultVOUtil.success(BackMessageEnum.SIGNOUT_SUCCESS.getMessage());
        }
        return resultVo;
    }

    /**
     * 获取个人一段时间的签到情况
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public ResultVO getSelfTimeInfo(String userId, String startDate, String endDate) {
        List<TimeInfo> timeInfoList = getTimeTableList(startDate, endDate, userId);
        User user = userMapper.getUserByStuId(userId);
        /* 拼装返回信息 */
        TimeTableVo timeTableVo = new TimeTableVo();
        List<SingleRecordVo> singleRecordVoList = togetherData(timeInfoList);
        timeTableVo.setSingleRecordVoList(singleRecordVoList);
        /* 计算总签到时长 */
        String totalTime = calculateTotalTime(singleRecordVoList, userId);
        timeTableVo.setTotalTime(totalTime);
        /* 合格次数 */
        int count = 0;
        for (int i = 0; i < singleRecordVoList.size(); i++) {
            if (VALID.equals(singleRecordVoList.get(i).getTimeValid()) || SUPP.equals(singleRecordVoList.get(i).getTimeValid())) {
                count++;
            }
        }
        String averageTime = DateUtil.calculateAverageTime(totalTime, count);
        timeTableVo.setAverageTime(averageTime);
        timeTableVo.setIsQualified(isQualify(userId, totalTime, averageTime));
        timeTableVo.setUnQualifyTimes(user.getUnqualifyTimes());
        return ResultVOUtil.success(timeTableVo);
    }

    /**
     * 签到页面展示
     * @param
     * @return
     */
    @Override
    public ResultVO getSignList() {
        String tempDate = DateUtil.getTimeDate();
        List<SignListVo> signListVoList = timeInfoMapper.getSignListByDate(tempDate);
        for(SignListVo signListVo : signListVoList) {
            /** 设置签到可用*/
            if(signListVo.getTimeState() != SIGN_OUT){
                signListVo.setTimeState(SIGN_IN);
            }
        }
        return ResultVOUtil.success(signListVoList);
    }


    @Override
    public ResultVO getAllTimeByDates(String startDate, String endDate) {
        /** 获取所有用户的用户名*/
        List<String> userNameList = userMapper.getAllUserName();

        /** 签到有效次数*/
        Integer timeFre = 0;
        String totalTime = "0-0";
        List<AllTimeListVo> allTimeListVoList = new ArrayList<>();
        for(String userName : userNameList){
            timeFre = 0;
            totalTime = "0-0";
            User user = userMapper.getUserByName(userName);
            AllTimeListVo allTimeListVo = new AllTimeListVo();
            allTimeListVo.setUserName(userName);
            SomeOneTimeVo someOneTimeVo = getOneTime(startDate, endDate, userName);

            /**计算总签到次数*/
            for(int i=0; i < someOneTimeVo.getSingleRecordVoList().size(); i++){
                if(VALID.equals(someOneTimeVo.getSingleRecordVoList().get(i).getTimeValid())){
                    timeFre++;
                }
            }
            allTimeListVo.setTimeFre(timeFre);

            /** 计算总时间*/
            totalTime = calculateTotalTime(someOneTimeVo.getSingleRecordVoList());
            List<AddInfo> addInfoList = addInfoMapper.getAllByUserId(user.getStuId());
            /** 将补签时间加入*/
            for(AddInfo addInfo : addInfoList){
                if(addInfo.getAddState().equals(SUPP))
                totalTime = DateUtil.calculateTotalTimeAddSupp(totalTime, addInfo.getAddTime());
            }
            allTimeListVo.setTotalTime(totalTime);
            String[] tempTotal = totalTime.split("-");
            allTimeListVo.setTotalHours(Integer.parseInt(tempTotal[0]));
            allTimeListVo.setTotalMinutes(Integer.parseInt(tempTotal[1]));

            /** 计算某人的平均打卡时间*/
            String avergeTime = DateUtil.calculateAverageTime(totalTime, timeFre);
            String[] tempA = avergeTime.split("-");
            allTimeListVo.setAHours(Integer.parseInt(tempA[0]));
            allTimeListVo.setAMinutes(Integer.parseInt(tempA[1]));

            /** 判断是否合格*/
            allTimeListVo.setIsQualified(isQualify(user.getStuId(), totalTime, avergeTime));
            allTimeListVo.setUnQualifyTimes(user.getUnqualifyTimes());
            allTimeListVoList.add(allTimeListVo);
        }
        return ResultVOUtil.success(allTimeListVoList);
    }

    @Override
    public SomeOneTimeVo getOneTime(String startDate, String endDate, String userName) {
        String userId = userMapper.getUserIdByName(userName);
        /** 从数据库中查找userId在日期内的签到信息记录 */
        List<TimeInfo> timeTableList = getTimeTableList(startDate, endDate, userId);
        /** 整理数据库中查询结果，拼装返回信息 */
        SomeOneTimeVo someOneTimeVo = new SomeOneTimeVo();
        someOneTimeVo.setUserName(userName);
        someOneTimeVo.setSingleRecordVoList(togetherData(timeTableList));
        return someOneTimeVo;
    }

    @Override
    public ResultVO getAnyTime(String startDate, String endDate) {
        List<String> userNameList = userMapper.getAllUserName();
        int timeFre = 0;
        List<AllTimeListVo> allTimeListVoList = new ArrayList<>();
        for(String userName : userNameList) {
            timeFre = 0;
            String totalTime = "0-0";
            /* 由用户名获取userId*/
            User user = userMapper.getUserByName(userName);
            AllTimeListVo allTimeListVo = new AllTimeListVo();
            allTimeListVo.setUserName(userName);
            /* 查询一个人的指定时间内的签到情况*/
            SomeOneTimeVo someOneTimeVo = getRenyiUserRecord(startDate, endDate, user.getStuId(), userName);

            for(int i=0; i<someOneTimeVo.getSingleRecordVoList().size(); i++) {
                if(someOneTimeVo.getSingleRecordVoList().get(i).getTimeValid().equals(VALID)) {
                    timeFre++;
                }
            }
            allTimeListVo.setTimeFre(timeFre);
            totalTime = calculateTotalTime(someOneTimeVo.getSingleRecordVoList());
            allTimeListVo.setTotalTime(totalTime);
            String[] temptotal = totalTime.split("-");
            allTimeListVo.setTotalHours(Integer.parseInt(temptotal[0]));
            allTimeListVo.setTotalMinutes(Integer.parseInt(temptotal[1]));
            /* 计算某人的平均时间 */
            String averageTime = DateUtil.calculateAverageTime(totalTime, timeFre);
            String[] tempA = averageTime.split("-");
            allTimeListVo.setAHours(Integer.parseInt(tempA[0]));
            allTimeListVo.setAMinutes(Integer.parseInt(tempA[1]));
            /* 该时间段打卡是否合格 */
            allTimeListVo.setIsQualified(isQualify(user.getStuId(), totalTime, averageTime));
            allTimeListVo.setUnQualifyTimes(user.getUnqualifyTimes());
            allTimeListVoList.add(allTimeListVo);
        }
        return null;
    }

    @Override
    public ResultVO getOneAWeekInfo(String userId) {
        /** 获取当前周周一的日期*/
        String startDate = DateUtil.getMonDay();
        /** 获取当前周周日的日期*/
        String endDate = DateUtil.getSunDay();
        List<TimeInfo> timeInfoList = getTimeTableList(startDate, endDate, userId);
        User user = userMapper.getUserByStuId(userId);
        /* 拼装返回信息 */
        TimeTableVo timeTableVo = new TimeTableVo();
        List<SingleRecordVo> singleRecordVoList = togetherData(timeInfoList);
        timeTableVo.setSingleRecordVoList(singleRecordVoList);
        /* 计算总签到时长 */
        String totalTime = calculateTotalTime(singleRecordVoList, userId);
        timeTableVo.setTotalTime(totalTime);
        /* 合格次数 */
        int count = 0;
        for (int i = 0; i < singleRecordVoList.size(); i++) {
            if (VALID.equals(singleRecordVoList.get(i).getTimeValid()) || SUPP.equals(singleRecordVoList.get(i).getTimeValid())) {
                count++;
            }
        }
        String averageTime = DateUtil.calculateAverageTime(totalTime, count);
        timeTableVo.setAverageTime(averageTime);
        timeTableVo.setIsQualified(isQualify(userId, totalTime, averageTime));
        timeTableVo.setUnQualifyTimes(user.getUnqualifyTimes());
        return ResultVOUtil.success(timeTableVo);
    }

    /**
     * 拼凑返回信息之setSingleRecordVoList
     *
     * @param timeTableList
     * @return
     */
    public List<SingleRecordVo> togetherData(List<TimeInfo> timeTableList) {
        List<SingleRecordVo> singleRecordVoList = new ArrayList<>();
        /** 循环遍历 */
        for (TimeInfo timeTable : timeTableList) {
            SingleRecordVo singleRecordVo = new SingleRecordVo();
            /** 日期 */
            singleRecordVo.setTimeDate(timeTable.getTimeDate());
            /** 签到时间 */
            singleRecordVo.setTimeIn(timeTable.getTimeIn());
            /** 签退时间不为空，否则set */
            if (timeTable.getTimeOut() != null) {
                if (VALID.equals(timeTable.getTimeValid())) {
                    /** 记录有效 */
                    singleRecordVo.setTimeOut(timeTable.getTimeOut());
                    /** 计算单条记录时长 */
                    String timeTotal = calculateTimeLen(timeTable.getTimeIn(), timeTable.getTimeOut());
                    singleRecordVo.setTimeTotal(timeTotal);
                    /** 获取时、分 */
                    String[] tempTime = timeTotal.split("-");
                    singleRecordVo.setHours(Integer.parseInt(tempTime[0]));
                    singleRecordVo.setMinutes(Integer.parseInt(tempTime[1]));
                } else {
                    /** 记录无效 */
                    singleRecordVo.setTimeOut(timeTable.getTimeOut());
                    singleRecordVo.setTimeTotal("0-0");
                }
                singleRecordVo.setTimeValid(timeTable.getTimeValid());
            } else {
                /** 没有签退，则该次记录时长为0 */
                singleRecordVo.setTimeTotal("0-0");
                singleRecordVo.setHours(0);
                singleRecordVo.setMinutes(0);
                singleRecordVo.setTimeValid(UNVALID);
            }
            singleRecordVoList.add(singleRecordVo);
        }
        return singleRecordVoList;
    }

    /**
     * 判断打卡时间是否合格
     *
     * @param totalTime
     * @return
     */
    public String isQualify(String userId, String totalTime, String averageTime) {
        String flag = UNVALID;
        User user = userMapper.getUserByStuId(userId);
        String[] tempTotal = totalTime.split("-");
        String[] averageStr = averageTime.split("-");
        if (Integer.parseInt(tempTotal[0]) >= user.getQualifyTime() && Integer.parseInt(averageStr[0]) < AVERAGETIMEHOUR) {
            flag = VALID;
        }
        return flag;
    }

    /**
     * 计算记录时长
     *
     * @param dateIn
     * @param dateOut
     * @return
     */
    public String calculateTimeLen(String dateIn, String dateOut) {
        Date date1 = DateUtil.convertStringToTime(dateIn);
        Date date2 = DateUtil.convertStringToTime(dateOut);
        String timeDiff = DateUtil.getTimeDiff(date1, date2);
        return timeDiff;
    }

    /**
     * 将所有时长加和
     *
     * @param singleRecordVoList
     * @return
     */
    public String calculateTotalTime(List<SingleRecordVo> singleRecordVoList, String userId) {
        String total = "0-0";
        /* 循环将每条记录时长加起来 */
        List<AddInfo> addInfoList = addInfoMapper.getAllByUserId(userId);
        for (int i = 0; i < singleRecordVoList.size(); i++) {
            if (VALID.equals(singleRecordVoList.get(i).getTimeValid())) {
                total = DateUtil.calculateTotalTimeDiff(total, singleRecordVoList.get(i).getTimeTotal());
            }
        }
        for (int j = 0; j< addInfoList.size(); j++) {
            if (SUPP.equals(addInfoList.get(j).getAddState())) {
                total = DateUtil.calculateTotalTimeAddSupp(total, addInfoList.get(j).getAddTime());
            }
        }
        return total;
    }

    public String calculateTotalTime(List<SingleRecordVo> singleRecordVoList) {
        String total = "0-0";
        /* 循环将每条记录时长加起来 */
        for (int i = 0; i < singleRecordVoList.size(); i++) {
            if (VALID.equals(singleRecordVoList.get(i).getTimeValid())) {
                total = DateUtil.calculateTotalTimeDiff(total, singleRecordVoList.get(i).getTimeTotal());
            }
        }
        return total;
    }

    /**
     * 判断用户所查是本周的，还是以前的
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public Boolean isAgo(String startDate, String endDate) {
        Boolean flag = false;
        DateTime s = new DateTime(startDate);
        DateTime e = new DateTime(endDate);
        Interval interval = new Interval(s, e);
        flag = interval.contains(DateTime.now());
        if (DateUtil.getTimeDate().equals(startDate)) {
            flag = true;
        } else if (DateUtil.getTimeDate().equals(endDate)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 查询某个人日期内的签到记录
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<TimeInfo> getTimeTableList(String startDate, String endDate, String userId) {
        List<TimeInfo> timeInfoList = null;
        if (isAgo(startDate, endDate)) {
            /* 查询出符合条件的签到记录 */
            timeInfoList = timeInfoMapper.getTablesByDate(startDate, endDate, userId);
        } else {
            timeInfoList = timeInfoMapper.getAgoTableByDate(startDate, endDate, userId);
        }
        return timeInfoList;
    }


    /**
     * 查询某人任意时间
     *
     * @param startDate
     * @param endDate
     * @param userId
     * @return
     */
    public SomeOneTimeVo getRenyiUserRecord(String startDate, String endDate, String userId, String userName) {
        SomeOneTimeVo someOneTimeVo = new SomeOneTimeVo();
        List<TimeInfo> timeTableList = new ArrayList<>();

        //新、旧表联查
        //查询旧表
        List<TimeInfo> list3 = getRecordListFromOldTable(startDate, endDate, userId);
        //查询新表
        List<TimeInfo> list4 = getRecordListFromNewTable(startDate, endDate, userId);
        timeTableList.addAll(list3);
        timeTableList.addAll(list4);

        someOneTimeVo.setUserName(userName);
        someOneTimeVo.setSingleRecordVoList(togetherData(timeTableList));

        return someOneTimeVo;
    }

    /**
     * 从旧表中获取某用户某日期范围内的记录
     *
     * @param startDate
     * @param endDate
     * @param userId
     * @return
     */
    public List<TimeInfo> getRecordListFromOldTable(String startDate, String endDate, String userId) {
        List<TimeInfo> oldTableRecordList = timeInfoMapper.getAgoTableByDate(startDate, endDate, userId);
        return oldTableRecordList;
    }

    /**
     * 从新表中获取某用户某日期范围内的记录
     *
     * @param startDate
     * @param endDate
     * @param userId
     * @return
     */
    public List<TimeInfo> getRecordListFromNewTable(String startDate, String endDate, String userId) {
        List<TimeInfo> newTableRecordList = timeInfoMapper.getTablesByDate(startDate, endDate, userId);
        return newTableRecordList;
    }
}
