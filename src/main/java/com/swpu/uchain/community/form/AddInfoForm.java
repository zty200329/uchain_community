package com.swpu.uchain.community.form;

import lombok.Data;

/**
 * @author；lzh
 * @Date:2019/8/710:49 Descirption:
 */
@Data
public class AddInfoForm {

    /** 申请人学号*/
    private String userId;

    /** 申请时间*/
    private String addTime;

    /** 申请补签日期*/
    private String addDate;

    /** 申请补签理由*/
    private String addExcuse;
}
