package com.swpu.uchain.community.util;

import com.swpu.uchain.community.dao.AddInfoMapper;
import com.swpu.uchain.community.dao.TimeInfoMapper;
import com.swpu.uchain.community.dao.UserMapper;
import com.swpu.uchain.community.entity.TimeInfo;
import com.swpu.uchain.community.entity.User;
import com.swpu.uchain.community.service.SignInService;
import com.swpu.uchain.community.vo.AllTimeListVo;
import com.swpu.uchain.community.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName TimedTask
 * @Description 定时任务
 */
@Component
@Slf4j
public class TimedTask {
    /**
     * 查询日期中，开始日期，是today日期的前7天
     */
    private static final int AGO_START = 7;
    /**
     * 查询日期中，结束日期，是today日期的前1天
     */
    private static final int AGO_END = 1;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TimeInfoMapper timeInfoMapper;
    @Autowired
    private SignInService signInService;
    @Autowired
    private AddInfoMapper addInfoMapper;

    /**
     * 每天零点将数据库中未签退的记录设为无效
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void timerTask() {
        DateTime today = new DateTime();
        System.out.println("当前时间为：" + today.toString("yyyy-MM-dd HH:mm:ss"));
        try {
            int i = timeInfoMapper.timerUpdate();
            log.info("[定时任务]: 无签退的更新记录有" + i + "条");
        } catch (Exception e) {
            log.error("[定时任务]: 操作数据库出错");
            e.printStackTrace();
        }
        log.info("[定时任务]: 结束");
    }

    /**
     * 每周日 将原本周的签到信息记录移动到旧时间表中，并删除半年之前的签到记录
     */
    @Transactional
    @Scheduled(cron = "0 0 0 ? * MON")
    public void moveTableTask() {
        DateTime today = new DateTime();
        System.out.println("当前时间为： " + today.toString("yyyy-MM-dd HH:mm:ss"));
        /** 获取本周周一的日期*/
        String startDate = today.dayOfWeek().withMinimumValue().toString("yyyy-mm-dd");
        /** 获取本周周日的日期*/
        String endDate = today.dayOfWeek().withMaximumValue().toString("yyyy-mm-dd");
       log.info("[移动数据定时任务]： 时间段为" + startDate + "～" + endDate);
        List<TimeInfo> timeTableList = timeInfoMapper.selectAll(startDate, endDate);
        try {
            if (timeTableList.size() != 0) {
                int i = timeInfoMapper.insertListToAgoTable(timeTableList);
                log.info("[移动数据定时任务]: 插入旧表共完成" + i + "条记录");
                int j = timeInfoMapper.deleteAll(startDate, endDate);
                log.info("[移动数据定时任务]: 删除新表共完成" + j + "条记录");
            } else {
                log.info("[移动数据定时任务]: 插入旧表共完成0条记录");
            }
            log.info("[定时任务]: 删除旧表180天前数据");
            int k = timeInfoMapper.deleteOneDayAgo();
            log.info("[定时任务]: 删除旧表共完成"+ k + "条记录");
        } catch (Exception e) {
            log.error("[移动数据定时任务]: 操作数据库出错");
            e.printStackTrace();
        }
        log.info("[移动数据定时任务]: 结束");

        log.info("[不合格处理定时任务]：开始");
        log.info("[不合格处理定时任务]: 时间日期位为" + today.minusDays(7).toString("yyyy-MM-dd") + "~" + today.minusDays(1).toString("yyyy-MM-dd"));

        ResultVO resultVO = signInService.getAllTimeByDates(today.minusDays(7).toString("yyyy-MM-dd"), today.minusDays(1).toString("yyyy-MM-dd"));
        List<AllTimeListVo> allTimeListVoList = (List<AllTimeListVo>) resultVO.getData();
        for (int i = 0; i < allTimeListVoList.size(); i++) {
            if ("N".equals(allTimeListVoList.get(i).getIsQualified())) {
                User user = userMapper.getUserByName(allTimeListVoList.get(i).getUserName());
                int number = user.getUnqualifyTimes() + 1;
                userMapper.updateTimes(user.getStuId(), number);
            } else {
                User user = userMapper.getUserByName(allTimeListVoList.get(i).getUserName());
                int number = 0;
                userMapper.updateTimes(user.getStuId(), number);
            }
        }
    }

    /**
     * 每周清空一次补签表
     */
    @Scheduled(cron = "0 0 0 ? * MON")
    public void cleanAddTable() {
        DateTime today = new DateTime();
        System.out.println("当前时间为： " + today.toString("yyyy-MM-dd HH:mm:ss"));
        /** 获取本周周一的日期*/
        String startDate = today.dayOfWeek().withMinimumValue().toString("yyyy-mm-dd");
        /** 获取本周周日的日期*/
        String endDate = today.dayOfWeek().withMaximumValue().toString("yyyy-mm-dd");
        List<TimeInfo> timeTableList = timeInfoMapper.selectAll(startDate, endDate);
        if(timeTableList.size()!=0){
            int result = addInfoMapper.deleteOneWeekAgo(startDate, endDate);
            log.info("共删除补签数据" + result + "条");
        }else{
            log.info("共删除补签数据0条");
        }
    }
}
