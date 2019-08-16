package com.swpu.uchain.community.form;

import lombok.Data;

/**
 * @author；lzh
 * @Date:2019/8/810:50 Descirption:
 */
@Data
public class GetSomeoneTimeForm {

    /**
     * 用户学号
     */
    private String userId;

    /**
     * 查询的起始时间
     */
    private String startDate;

    /**
     * 查询的截止时间
     */
    private String endDate;
}
