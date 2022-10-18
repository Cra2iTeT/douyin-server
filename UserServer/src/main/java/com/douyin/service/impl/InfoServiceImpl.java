package com.douyin.service.impl;

import com.alibaba.fastjson.JSON;
import com.douyin.constant.RedisConstant;
import com.douyin.constant.ServiceEnum;
import com.douyin.domain.entity.UserFollowInfo;
import com.douyin.domain.to.ServiceR;
import com.douyin.domain.vo.UserInfoVo;
import com.douyin.service.AuthService;
import com.douyin.service.InfoService;
import com.douyin.utils.NumberUtil;
import com.douyin.utils.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Cra2iTeT
 * @date 2022/10/16 10:46
 */
@Service("InfoService")
public class InfoServiceImpl implements InfoService {

    private final AuthService authService;

    private final StringRedisTemplate stringRedisTemplate;

    private final MongoTemplate mongoTemplate;

    public InfoServiceImpl(AuthService authService, StringRedisTemplate stringRedisTemplate,
                           MongoTemplate mongoTemplate) {
        this.authService = authService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ServiceR getUserInfoByToken(String token) {
        UserInfoVo userInfoVo = authService.getUserInfoVoByToken(token);
        if (userInfoVo == null) {
            return new ServiceR(ServiceEnum.USER_LOGIN_NULL.getCode(), ServiceEnum.USER_LOGIN_NULL.getMsg());
        }
        return new ServiceR(ServiceEnum.USER_SUCCESS.getCode(), ServiceEnum.USER_SUCCESS.getMsg(),
                JSON.toJSONString(userInfoVo));
    }

    @Override
    public ServiceR followOrCancelWithIdByToken(String _id, String token) {
        UserInfoVo followUserInfoVo = authService.getUserInfoVoByToken(token);
        if (followUserInfoVo == null) {
            return new ServiceR(ServiceEnum.USER_LOGIN_NULL.getCode(), ServiceEnum.USER_LOGIN_NULL.getMsg());
        }
        BoundHashOperations<String, Object, Object> followInfoHashOps = stringRedisTemplate
                .boundHashOps(RedisConstant.USER_FOLLOWED_INFO + followUserInfoVo.get_id());
        Boolean isFollow = (Boolean) followInfoHashOps.get(_id);
        String followedUserInfoVoRedis = (String) stringRedisTemplate
                .boundHashOps(RedisConstant.USER_INFO_ID + _id).get("info");
        UserInfoVo followedUserInfoVo;
        if (!StringUtils.isEmpty(followedUserInfoVoRedis)) {
            followedUserInfoVo = JSON.parseObject(followedUserInfoVoRedis, UserInfoVo.class);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            followedUserInfoVo = mongoTemplate.findOne(query, UserInfoVo.class);
        }
        BigDecimal oldFollowNum = new BigDecimal(followUserInfoVo.getFollowNum());
        BigDecimal oldFollowedNum = new BigDecimal(followedUserInfoVo.getFollowedNum());
        BigDecimal newFollowNum, newFollowedNum;
        if (isFollow == null || !isFollow) {
            newFollowNum = oldFollowNum.add(BigDecimal.valueOf(1));
            newFollowedNum = oldFollowedNum.add(BigDecimal.valueOf(1));
        } else {
            newFollowNum = oldFollowNum.subtract(BigDecimal.valueOf(1));
            newFollowedNum = oldFollowedNum.subtract(BigDecimal.valueOf(1));
        }
        followUserInfoVo.setFollowNum(String.valueOf(newFollowNum));
        followedUserInfoVo.setFollowedNum(String.valueOf(newFollowedNum));

        saveFollowInfo2Database(followUserInfoVo.get_id(), _id, Boolean.FALSE.equals(isFollow));
        saveFollowInfo2Redis(followInfoHashOps, followUserInfoVo.get_id(),
                _id, Boolean.FALSE.equals(isFollow));
        return new ServiceR(ServiceEnum.USER_SUCCESS.getCode(), ServiceEnum.USER_SUCCESS.getMsg());
    }

    private void saveFollowInfo2Database(String followUser_id, String followedUser_id, boolean isFollow) {
        UserFollowInfo userFollowInfo;
        if (!isFollow) {
            Query query = new Query().addCriteria(Criteria.where("followed_user_id").is(followedUser_id)
                    .andOperator(Criteria.where("follow_user_id").is(followUser_id)));
            Update update = new Update().set("is_follow", false);
            mongoTemplate.updateFirst(query, update, UserFollowInfo.class);
        } else {
            userFollowInfo = genNewUserFollowByD_id(followUser_id, followedUser_id);
            mongoTemplate.insert(userFollowInfo, "douyin.user.follow");
        }
    }

    private UserFollowInfo genNewUserFollowByD_id(String followUser_id,
                                                  String followedUser_id) {
        UserFollowInfo userFollowInfo = new UserFollowInfo();
        userFollowInfo.set_id(NumberUtil.genId4Str());
        userFollowInfo.setFollowUser_id(followUser_id);
        userFollowInfo.setFollowedUser_id(followedUser_id);
        userFollowInfo.setIsFollow(true);
        userFollowInfo.setCreateTime(TimeUtil.getTimeInMillis4Str());
        userFollowInfo.setUpdateTime(TimeUtil.getTimeInMillis4Str());
        return userFollowInfo;
    }

    private void saveFollowInfo2Redis(BoundHashOperations<String, Object, Object> followInfoHashOps,
                                      String followUser_id,
                                      String followedUser_id,
                                      Boolean isFollow) {
        followInfoHashOps.put(followedUser_id, isFollow);

        BoundHashOperations<String, Object, Object> followedInfoHashOps = stringRedisTemplate
                .boundHashOps(RedisConstant.USER_FOLLOW_INFO + followedUser_id);
        followedInfoHashOps.put(followUser_id, isFollow);
    }
}
