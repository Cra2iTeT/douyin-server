package com.douyin;

import com.douyin.domain.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
class UserServerApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
        UserInfo userInfo = new UserInfo();
        userInfo.set_id("12313");
        userInfo.setEmail("131531");
        userInfo.setGender(0);
        userInfo.setFollowNum("545");
        mongoTemplate.insert(userInfo, "douyin.user.info");
    }

}
