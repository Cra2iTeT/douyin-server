package com.douyin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
class GateWayServerApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;


}
