package com.douyin.domain.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Cra2iTeT
 * @date 2022/10/16 17:03
 */
@Data
public class UserFollowInfo {
    private String _id;
    @Field("follow_user_id")
    private String followUser_id;
    @Field("followed_user_id")
    private String followedUser_id;
    @Field("is_follow")
    private Boolean isFollow;
    @Field("create_time")
    private String createTime;
    @Field("update_time")
    private String updateTime;
}
