package com.swpu.uchain.community.vo;

import lombok.Data;

/**
 * @ClassName SignListVo
 * @Description 签到页面的签到列表
 * @Author LZH
 */
@Data
public class SignListVo {
    /**
     * 姓名
     */
    private String userName;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 签到编号
     */
    private String timeId;
    /**
     * 签到签退状态
     */
    private int timeState;
}
