package com.swpu.uchain.community.enums;

import lombok.Getter;

/**
 * 正常信息返回枚举
 *
 * @author LZH
 */
@Getter
public enum BackMessageEnum {
    /**
     * 登录成功
     */
    LOGIN_SUCCESS(1, "登录成功"),
    /**
     * 修改信息成功
     */
    MODIFY_SUCCESS(2, "修改成功"),
    /**
     * 管理员添加成功
     */
    ADD_SUCCESS(3, "添加成功"),
    /**
     * 管理员删除成功
     */
    DEL_SUCCESS(4, "删除成功"),
    /**
     * 重置密码
     */
    RESET_SUCCESS(7, "重置成功"),
    /**
     * 签到
     */
    SIGNIN_SUCCESS(8, "签到成功"),
    /**
     * 签退
     */
    SIGNOUT_SUCCESS(9, "签退成功"),
    /**
     * 授权
     */
    GRANT_SUCCESS(10, "授权成功"),
    /**
     * 取消授权
     */
    CANCEL_SUCCESS(11, "取消成功"),
    /**
     * 补签
     */
    SUPPLEMENT_SUCCESS(12, "补签记录已提交，请等待管理员审批！"),
    /**
     * 管理员通过审批
     */
    PASS_SUCCESS(13, "已通过"),
    /**
     * 管理员拒绝
     */
    DENY_SUCCESS(14, "已拒绝"),;
    private Integer code;

    private String message;

    BackMessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
