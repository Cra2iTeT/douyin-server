package com.douyin.domain.to;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Cra2iTeT
 * @date 2022/10/16 10:53
 */
@Data
public class UserInfoTo {
    private String phone;
    private String nickName;
    private int gender;
    private String email;
}
