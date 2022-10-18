package com.douyin.constant;

/**
 * @author Cra2iTeT
 * @date 2022/10/14 10:06
 */
public class RedisConstant {

    public static final String EMAIL_CODE_LOGIN = "email:code:login:";
    public static final Long EMAIL_CODE_LOGIN_TTL = 90L;

    public static final String USER_TOKEN = "user:token:";
    public static final Long USER_TOKEN_TTL = 3600L;

    public static final String USER_INFO_ID = "user:info:_id:";

    public static final String USER_FOLLOW_INFO = "user:follow:info";
    public static final String USER_FOLLOWED_INFO = "user:followed:info";
}
