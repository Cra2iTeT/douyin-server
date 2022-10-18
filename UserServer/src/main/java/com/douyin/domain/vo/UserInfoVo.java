package com.douyin.domain.vo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Cra2iTeT
 * @date 2022/10/14 12:13
 */
@Data
public class UserInfoVo {
    private String _id;
    private String phone;
    @Field("nick_name")
    private String nickName;
    private int gender;
    @Field("like_num")
    private String likeNum;
    @Field("follow_num")
    private String followNum;
    @Field("followed_num")
    private String followedNum;
    private int status;
    @Field("create_time")
    private String createTime;
    private String email;
}
