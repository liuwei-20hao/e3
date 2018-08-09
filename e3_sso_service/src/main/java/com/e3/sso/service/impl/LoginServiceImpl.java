package com.e3.sso.service.impl;

import com.e3.mapper.TbUserMapper;
import com.e3.pojo.E3Result;
import com.e3.pojo.JedisClient;
import com.e3.pojo.JsonUtils;
import com.e3.pojo.TbUser;
import com.e3.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;

    @Override
    public E3Result login(String username, String password) {
        // 1、判断用户和密码是否正确
        //根据用户名查询用户信息
        List<TbUser> list = userMapper.selectUserByUserName(username);
        if (list == null || list.size() == 0) {
            //返回登录失败
            return E3Result.build(400, "用户名或密码错误");
        }
        //取用户信息
        TbUser user = list.get(0);
        //判断密码是否正确
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            // 2、如果不正确，返回登录失败
            return E3Result.build(400, "用户名或密码错误");
        }
        // 3、如果正确生成token。
        String token = UUID.randomUUID().toString();
        // 4、把用户信息写入redis，key：token value：用户信息
        user.setPassword(null);
        jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));
        // 5、设置Session的过期时间
        jedisClient.expire("SESSION:" + token, 600);
        // 6、把token返回

        return E3Result.ok(token);
    }
}
