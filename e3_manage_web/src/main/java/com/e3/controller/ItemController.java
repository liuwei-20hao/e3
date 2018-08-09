package com.e3.controller;

import com.e3.pojo.E3Result;
import com.e3.pojo.EasyUIDataGridResult;
import com.e3.pojo.TbItem;
import com.e3.service.ItemService;
import com.fasterxml.jackson.annotation.JacksonInject;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	//beta
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getTbItemById(itemId);
		return tbItem;
	}

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //调用服务查询商品列表
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

	@RequestMapping("/item/save")
	@ResponseBody
	public E3Result addItem(TbItem tbItem,String desc) {
		E3Result e3Result = itemService.addTtem(tbItem,desc);
		return e3Result;
	}
}
