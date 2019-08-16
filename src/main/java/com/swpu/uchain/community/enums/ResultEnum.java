package com.swpu.uchain.community.enums;

import lombok.Getter;

/**
 * @author hobo
 * @description
 */
@Getter
public enum ResultEnum {
    AUTHENTICATION_ERROR(401, "用户认证失败,请重新登录"),
    PERMISSION_DENNY(403, "权限不足"),
    NOT_FOUND(404, "url错误,请求路径未找到"),
    SERVER_ERROR(500, "服务器未知错误:%s"),
    BIND_ERROR(511, "参数校验错误:%s"),
    REQUEST_METHOD_ERROR(550, "不支持%s的请求方式"),
    USER_NOT_EXIST(1,"用户不存在" ), PASSWORD_ERROR(2,"密码错误" ),
    /**
     * 失败
     */
    SUCCESS(0,"成功"),
    /**
     * 成功
     */
    ERROR(1,"失败"),
    /**
     * 签退失败
     */
    SIGNOUT_ERROR(2,"签退失败"),
    /**
     * 服务器内部错误，数据库操作出错
     */
    SQL_ERROR(4, "增删查改操作数据库出错"),

    /**
     * 签到签退的日期不同
     */
    SIGNIN_OUT(5, "该条签到记录已过期，不能签退"),

    /**
     * 添加用户，判断是否存在
     */
    USERID_HAS_EXIT(6, "该用户已存在"),

    /**
     * 修改密码，原密码错误
     */
    OLDPASSWORD_ERROR(7, "原密码错误"),

    /**
     * 签到记录存在当天未签退，就签到
     *
     */
    SIGNIN_ERROR(8, "有未签退的记录，请先签退"),

    /**
     * 补签日期提交错误
     */
    SUPPLEMENT_DATE_ERROR(9, "补签日期只能在本周之内"),
    /**
     * 签到
     */
    SIGNIN_SUCCESS(10, "签到成功"),
    /**
     * 签退
     */
    SIGNOUT_SUCCESS(11, "签退成功"),
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
    DENY_SUCCESS(14, "已拒绝"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
