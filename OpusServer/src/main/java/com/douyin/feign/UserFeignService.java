package com.douyin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Cra2iTeT
 * @date 2022/10/13 18:04
 */
@FeignClient("userServer")
public interface UserFeignService {
    @RequestMapping("/user/auth/test")
    String test();
}
