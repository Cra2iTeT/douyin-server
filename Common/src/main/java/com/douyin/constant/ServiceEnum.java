package com.douyin.constant;

/**
 * @author Cra2iTeT
 * @date 2022/10/14 9:52
 */
public enum ServiceEnum {

    UNKNOWN_EXCEPTION(10000, "系统未知错误"),
    VALID_EXCEPTION(10001, "参数校验异常"),

    USER_SUCCESS(15000, "用户业务成功"),
    USER_LOGIN_ACCOUNT_PASSWORD_INVALID(15001, "账号或密码错误"),
    USER_SMS_CODE_LOGIN_INVALID(15002, "验证码错误"),
    USER_LOGIN_PHONE_INVALID(15003, "手机号格式不正确"),
    USER_LOGIN_ACCOUNT_NULL(15004, "手机号未注册"),
    USER_LOGIN_EMAIL_INVALID(15005, "邮箱号格式错误"),
    USER_LOGIN_EMAIL_NULL(15006, "邮箱号未注册"),
    USER_FAILED(15007, "用户业务失败"),
    USER_SMS_CODE_LOGIN_REPEAT(15008, "请勿频繁重复获取验证码"),
    USER_LOGIN_NULL(15009, "当前用户未登录");

    private Integer code;
    private String msg;

    ServiceEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
