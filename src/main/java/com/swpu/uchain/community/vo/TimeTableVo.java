package com.swpu.uchain.community.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName TimeTableVo
 * @Description 用户查询个人签到记录情况返回视图
 * @Author LZH
 */
@Data
public class TimeTableVo {
    /**
     * 单条记录列表
     */
    private List<SingleRecordVo> singleRecordVoList;
    /**
     * 总时长
     */
    private String totalTime;
    /**
     * 平均时长
     */
    private String averageTime;
    /**
     * 是否有效
     */
    private String isQualified;
    /**
     * 连续不合格次数
     */
    private Integer unQualifyTimes;

}
