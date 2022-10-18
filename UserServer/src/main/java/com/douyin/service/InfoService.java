package com.douyin.service;

import com.douyin.domain.to.ServiceR;

/**
 * @author Cra2iTeT
 * @date 2022/10/16 10:45
 */
public interface InfoService {
    ServiceR getUserInfoByToken(String token);

    ServiceR followOrCancelWithIdByToken(String _id, String token);
}
