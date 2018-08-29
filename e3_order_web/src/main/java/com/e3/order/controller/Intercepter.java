package com.e3.order.controller;

import com.e3.cart.service.CartService;
import com.e3.pojo.*;
import com.e3.sso.service.TolenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Intercepter implements HandlerInterceptor{

    @Autowired
    private TolenService tokenService;
    @Autowired
    private CartService cartService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中取token
        String token = CookieUtils.getCookieValue(request, "token");
        //判断token是否存在
        if (StringUtils.isBlank(token)) {
            //如果token不存在，未登录状态，跳转到sso系统的登录页面。用户登录成功后，跳转到当前请求的url
            response.sendRedirect("http://localhost:8088/page/login?redirect=" + request.getRequestURL());
            //拦截
            return false;
        }
        //如果token存在，需要调用sso系统的服务，根据token取用户信息
        E3Result e3Result = tokenService.getUserByToken(token);
        //如果取不到，用户登录已经过期，需要登录。
        if (e3Result.getStatus() != 200) {
            //如果token不存在，未登录状态，跳转到sso系统的登录页面。用户登录成功后，跳转到当前请求的url
            response.sendRedirect( "http://localhost:8088/page/login?redirect=" + request.getRequestURL());
            //拦截
            return false;
        }
        //如果取到用户信息，是登录状态，需要把用户信息写入request。
        TbUser user = (TbUser) e3Result.getData();
        request.setAttribute("user", user);
        //判断cookie中是否有购物车数据，如果有就合并到服务端。
        String jsonCartList = CookieUtils.getCookieValue(request, "cart", true);
        if (StringUtils.isNoneBlank(jsonCartList)) {
            //合并购物车
            cartService.mergeCartList(user.getId(), JsonUtils.jsonToList(jsonCartList, TbItem.class));
        }
        //放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //handler执行之后，返回ModeAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //完成处理，返回ModelAndView之后。
        //可以再此处理异常
    }
}
