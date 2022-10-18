package com.douyin.service;

import com.douyin.domain.to.ServiceR;
import com.douyin.domain.to.LoginFormTo;
import com.douyin.domain.vo.UserInfoVo;

/**
 * @author Cra2iTeT
 * @date 2022/10/13 16:58
 */
public interface AuthService {
    ServiceR genEmailCode(String email);

    ServiceR loginOrRegisterWithCode(LoginFormTo loginFormTo);

    ServiceR loginOrRegisterWithPwd(LoginFormTo loginFormTo);

    UserInfoVo getUserInfoVoByToken(String token);
}
