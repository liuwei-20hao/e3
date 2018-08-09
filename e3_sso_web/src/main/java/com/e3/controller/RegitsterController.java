package com.e3.controller;

import com.e3.pojo.E3Result;
import com.e3.pojo.TbUser;
import com.e3.sso.service.RegitsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 注册功能Controller
 * <p>Title: RegitsterController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class RegitsterController {
	@Autowired
	private RegitsterService registerService;

	@RequestMapping("/page/register")
	public String showRegister() {
		return "register";
	}

	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String param, @PathVariable Integer type) {
		E3Result e3Result = registerService.checkData(param, type);
		return e3Result;
	}

	@RequestMapping(value ="/user/register",method = RequestMethod.POST)
	@ResponseBody
	public E3Result regitster(TbUser tbUser) {
		E3Result e3Result = registerService.register(tbUser);
		return e3Result;
	}
}
