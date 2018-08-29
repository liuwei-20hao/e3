package com.e3.controller;

import com.e3.pojo.CookieUtils;
import com.e3.pojo.E3Result;
import com.e3.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginContorller {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/page/login")
    public String showLogin(String redirect, Model model) {
        model.addAttribute("redirect",redirect);
        return "login";
    }

    @RequestMapping(value="/user/login", method= RequestMethod.POST)
    @ResponseBody
    public E3Result login(String username, String password,
                          HttpServletRequest request, HttpServletResponse response) {
        E3Result e3Result = loginService.login(username, password);
        //判断是否登录成功
        if(e3Result.getStatus() == 200) {
            String token = e3Result.getData().toString();
            //如果登录成功需要把token写入cookie
            CookieUtils.setCookie(request, response, "token", token);
        }
        //返回结果
        return e3Result;
    }
}
