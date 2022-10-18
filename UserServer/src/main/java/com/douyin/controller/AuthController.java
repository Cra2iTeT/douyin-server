package com.douyin.controller;

import com.douyin.constant.ServiceEnum;
import com.douyin.domain.to.ServiceR;
import com.douyin.domain.vo.R;
import com.douyin.service.AuthService;
import com.douyin.domain.to.LoginFormTo;
import com.douyin.valid.GenCode;
import com.douyin.valid.LoginWithCode;
import com.douyin.valid.LoginWithPwd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cra2iTeT
 * @date 2022/10/13 16:59
 */
@RestController
@RequestMapping("/user/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping("/test")
    public String test() {
        return "ss";
    }

    @PostMapping("/email/code")
    R genEmailCode(@Validated({GenCode.class}) @RequestBody LoginFormTo loginFormTo) {
        ServiceR serviceR = authService.genEmailCode(loginFormTo.getEmail());
        if (!ServiceEnum.USER_SUCCESS.getCode().equals(serviceR.getCode())) {
            return R.error(ServiceEnum.USER_FAILED.getCode(), ServiceEnum.USER_FAILED.getMsg());
        }
        return R.ok();
    }

    @PostMapping("/loginWithCode")
    R loginAndRegisterWithCode(@Validated({LoginWithCode.class}) @RequestBody LoginFormTo loginFormTo) {
        ServiceR serviceR = authService.loginOrRegisterWithCode(loginFormTo);

        if (!ServiceEnum.USER_SUCCESS.getCode().equals(serviceR.getCode())) {
            return R.error(ServiceEnum.USER_FAILED.getCode(), ServiceEnum.USER_FAILED.getMsg());
        }
        return R.ok().setData(serviceR.getData());
    }

    @PostMapping("/loginWithPwd")
    R loginAndRegisterWithPwd(@Validated({LoginWithPwd.class}) @RequestBody LoginFormTo loginFormTo) {
        ServiceR serviceR = authService.loginOrRegisterWithPwd(loginFormTo);

        if (!ServiceEnum.USER_SUCCESS.getCode().equals(serviceR.getCode())) {
            return R.error(ServiceEnum.USER_FAILED.getCode(), ServiceEnum.USER_FAILED.getMsg());
        }
        return R.ok().setData(serviceR.getData());
    }

}
