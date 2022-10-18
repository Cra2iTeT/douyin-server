package com.douyin.controller;

import com.douyin.feign.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cra2iTeT
 * @date 2022/10/13 18:06
 */
@RestController
public class Test {
    @Autowired
    private UserFeignService userFeignService;

    @GetMapping("/test")
    String test() {
        return userFeignService.test();
    }
}
