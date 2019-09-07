package com.swpu.uchain.community.dto;

import lombok.Data;

/**
 * @author；lzh
 * @Date:2019/8/615:52 Descirption:
 */
@Data
public class AddInfoDTO {

    /** 申请人学号*/
    private String userId;

    /** 申请时间*/
    private String addTime;

    /** 申请补签日期*/
    private String addDate;

    /** 申请补签理由*/
    private String addExcuse;

    /** 申通状态 默认0为未办理，1为通过，2为不通过*/
    private Integer addState;

}
