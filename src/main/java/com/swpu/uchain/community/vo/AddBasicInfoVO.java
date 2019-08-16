package com.swpu.uchain.community.vo;

import lombok.Data;

/**
 * @author；lzh
 * @Date:2019/8/819:40 Descirption:
 */
@Data
public class AddBasicInfoVO {

    /**
     * 申请的补签时间
     */
    private String addTime;

    /**
     * 申请发起的日期
     */
    private String addDate;

    /**
     * 申请状态
     */
    private Integer addState;
}
