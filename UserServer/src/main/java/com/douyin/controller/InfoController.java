package com.douyin.controller;

import com.douyin.constant.ServiceEnum;
import com.douyin.domain.to.ServiceR;
import com.douyin.domain.to.UserInfoTo;
import com.douyin.domain.vo.R;
import com.douyin.service.InfoService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cra2iTeT
 * @date 2022/10/16 10:43
 */
@RestController
@RequestMapping("/user/info")
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/get")
    R getUserInfoByToken(HttpServletRequest request) {
        ServiceR serviceR = infoService.getUserInfoByToken(request.getHeader("authorization"));
        if (serviceR.getCode().equals(ServiceEnum.USER_LOGIN_NULL.getCode())) {
            return R.error(ServiceEnum.USER_LOGIN_NULL.getCode(), ServiceEnum.USER_LOGIN_NULL.getMsg());
        }
        return R.ok().setData(serviceR.getData());
    }

    // TODO PC端没有，后续如果有手机端的话再做
    @PostMapping("/updateInfo")
    R updateInfo(@RequestBody UserInfoTo userInfoTo) {
        return R.ok();
    }

    @GetMapping("/follow/{_id}")
    R followOrCancelWithIdByToken(@PathVariable String _id, HttpServletRequest request) {
        ServiceR serviceR = infoService.followOrCancelWithIdByToken(_id, request.getHeader("authorization"));
        if (!serviceR.getCode().equals(ServiceEnum.USER_SUCCESS.getCode())) {
            return R.error(serviceR.getCode(), serviceR.getMsg());
        }
        return R.ok();
    }
}
