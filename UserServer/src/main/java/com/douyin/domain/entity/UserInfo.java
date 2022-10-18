package com.douyin.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Cra2iTeT
 * @date 2022/10/13 16:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "douyin.user.info")
public class UserInfo {
    private String _id;
    private String phone;
    @Field("pwd_md5")
    private String pwdMd5;
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
    @Field("update_time")
    private String updateTime;
    private String email;
}
