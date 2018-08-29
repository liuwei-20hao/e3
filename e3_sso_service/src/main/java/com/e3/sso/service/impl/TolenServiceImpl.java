package com.e3.sso.service.impl;

import com.e3.pojo.E3Result;
import com.e3.pojo.JedisClient;
import com.e3.pojo.JsonUtils;
import com.e3.pojo.TbUser;
import com.e3.sso.service.TolenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TolenServiceImpl implements TolenService {
    @Autowired
    private JedisClient jedisClient;
    @Override
    public E3Result getUserByToken(String token) {
        //根据token到redis中取用户信息
        String json = jedisClient.get("SESSION:" + token);
        //取不到用户信息，登录已经过期，返回登录过期
        if (StringUtils.isBlank(json)) {
            return E3Result.build(201, "用户登录已经过期;");
        }
        //取到用户信息更新token的过期时间
        jedisClient.expire("SESSION:" + token, 600);
        //返回结果，E3Result其中包含TbUser对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return E3Result.ok(user);
    }
}
