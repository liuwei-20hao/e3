package com.e3.item.controller;

import com.e3.item.pojo.Item;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbItemDesc;
import com.e3.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品详情页面展示Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId, Model model) {
		//调用服务取商品基本信息
		TbItem tbItem = itemService.getTbItemById(itemId);
		Item item = new Item(tbItem);
		//取商品描述信息
		TbItemDesc itemDesc = itemService.getTbItemDescById(itemId);
		//把信息传递给页面
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		//返回逻辑视图
		return "item";
	}
}
