package com.douyin.service.impl;

import com.alibaba.fastjson.JSON;
import com.douyin.constant.RedisConstant;
import com.douyin.constant.ServiceEnum;
import com.douyin.domain.entity.UserInfo;
import com.douyin.domain.to.LoginFormTo;
import com.douyin.domain.to.ServiceR;
import com.douyin.domain.to.UserInfoTo;
import com.douyin.domain.vo.UserInfoVo;
import com.douyin.service.AuthService;
import com.douyin.utils.MD5Util;
import com.douyin.utils.NumberUtil;
import com.douyin.utils.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Cra2iTeT
 * @date 2022/10/13 16:59
 */
@Service("AuthService")
public class AuthServiceImpl implements AuthService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendEmailUsername;

    private final StringRedisTemplate stringRedisTemplate;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AuthServiceImpl(JavaMailSender javaMailSender, StringRedisTemplate stringRedisTemplate, MongoTemplate mongoTemplate) {
        this.javaMailSender = javaMailSender;
        this.stringRedisTemplate = stringRedisTemplate;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ServiceR genEmailCode(String email) {
        String code = NumberUtil.genRandomNum4Str(6);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("douyin");
        mailMessage.setFrom(sendEmailUsername);
        mailMessage.setTo(email);
        mailMessage.setText(code);
        mailMessage.setSentDate(TimeUtil.getDateNow4Date());

        javaMailSender.send(mailMessage);

        stringRedisTemplate.opsForValue().set(RedisConstant.EMAIL_CODE_LOGIN + email, code,
                RedisConstant.EMAIL_CODE_LOGIN_TTL, TimeUnit.SECONDS);

        return new ServiceR(ServiceEnum.USER_SUCCESS.getCode(),
                ServiceEnum.USER_SUCCESS.getMsg());
    }

    @Override
    public ServiceR loginOrRegisterWithCode(LoginFormTo loginFormTo) {
        String codeRedis = stringRedisTemplate.opsForValue()
                .get(RedisConstant.EMAIL_CODE_LOGIN + loginFormTo.getEmail());

        if (!Objects.equals(codeRedis, loginFormTo.getCode())) {
            return new ServiceR(ServiceEnum.USER_SMS_CODE_LOGIN_INVALID.getCode(),
                    ServiceEnum.USER_SMS_CODE_LOGIN_INVALID.getMsg());
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(loginFormTo.getEmail()));

        UserInfo userInfo = mongoTemplate.findOne(query, UserInfo.class);
        String token;

        if (userInfo == null) {
            userInfo = genNewUserInfo(loginFormTo.getEmail());
        }
        token = NumberUtil.genTokenWithId(userInfo.get_id());

        saveUserLoginOrRegisterInfo2Redis(userInfo, token);

        return new ServiceR(ServiceEnum.USER_SUCCESS.getCode(), ServiceEnum.USER_SUCCESS.getMsg()
                , token);
    }

    @Override
    public ServiceR loginOrRegisterWithPwd(LoginFormTo loginFormTo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(loginFormTo.getEmail()));
        UserInfo userInfo = mongoTemplate.findOne(query, UserInfo.class);
        if (userInfo == null) {
            return new ServiceR(ServiceEnum.USER_LOGIN_EMAIL_NULL.getCode(),
                    ServiceEnum.USER_LOGIN_EMAIL_NULL.getMsg());
        }
        String pwdMd5 = MD5Util.MD5Encode(loginFormTo.getPwd(), "UTF-8");
        if (!Objects.equals(pwdMd5, userInfo.getPwdMd5())) {
            return new ServiceR(ServiceEnum.USER_LOGIN_ACCOUNT_PASSWORD_INVALID.getCode(),
                    ServiceEnum.USER_LOGIN_ACCOUNT_PASSWORD_INVALID.getMsg());
        }
        String token = NumberUtil.genTokenWithId(userInfo.get_id());

        saveUserLoginOrRegisterInfo2Redis(userInfo, token);

        return new ServiceR(ServiceEnum.USER_SUCCESS.getCode(), ServiceEnum.USER_SUCCESS.getMsg(),
                token);
    }

    private void saveUserLoginOrRegisterInfo2Redis(UserInfo userInfo, String token) {
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);

        stringRedisTemplate.opsForValue().set(RedisConstant.USER_TOKEN + token,
                JSON.toJSONString(userInfoVo), RedisConstant.USER_TOKEN_TTL, TimeUnit.MINUTES);
        stringRedisTemplate.opsForHash().put(RedisConstant.USER_INFO_ID + userInfoVo.get_id(),
                "info", JSON.toJSONString(userInfoVo));
        stringRedisTemplate.opsForHash().put(RedisConstant.USER_INFO_ID + userInfoVo.get_id(),
                "token", token);
    }

    @Override
    public UserInfoVo getUserInfoVoByToken(String token) {
        String userInfoVoRedis = stringRedisTemplate.opsForValue().get(RedisConstant.USER_TOKEN + token);
        if (StringUtils.isEmpty(userInfoVoRedis)) {
            return null;
        }
        return JSON.parseObject(userInfoVoRedis, UserInfoVo.class);
    }

    private UserInfo genNewUserInfo(String email) {
        UserInfo userInfo = new UserInfo();
        userInfo.set_id(NumberUtil.genId4Str());
        userInfo.setGender(0);
        userInfo.setEmail(email);
        userInfo.setFollowNum("0");
        userInfo.setLikeNum("0");
        userInfo.setFollowedNum("0");
        userInfo.setPhone("");
        userInfo.setCreateTime(TimeUtil.getDateNow4String());
        userInfo.setUpdateTime(TimeUtil.getDateNow4String());
        userInfo.setStatus(0);
        userInfo.setNickName("新用户_" + NumberUtil.genRandomNum4Str(8));
        userInfo.setPwdMd5(MD5Util.MD5Encode(NumberUtil.genRandomNum4Str(12), "UTF-8"));
        return userInfo;
    }
}
