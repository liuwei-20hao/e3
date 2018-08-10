package com.e3.controller;

import com.e3.pojo.E3Result;
import com.e3.pojo.EasyUIDataGridResult;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbItemDesc;
import com.e3.service.ItemService;
import com.fasterxml.jackson.annotation.JacksonInject;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

	@RequestMapping("/rest/item/param/item/query/")
	@ResponseBody
	public TbItem getItem(long id) {
		//调用服务查询商品
		TbItem result = itemService.getTbItemById(id);
		return result;
	}

	@RequestMapping("/rest/item/query/item/desc/")
	@ResponseBody
	public TbItemDesc getItemDesc(long id) {
		//调用服务查询商品描述
		TbItemDesc result = itemService.getTbItemDescById(id);
		return result;
	}


	@RequestMapping("/rest/item/update")
	@ResponseBody
	public E3Result updateItem(TbItem tbItem,String desc) {
		E3Result e3Result = itemService.updateTtem(tbItem,desc);
		return e3Result;
	}


	@RequestMapping("/item/delete")
	@ResponseBody
	public E3Result deleteItem(@RequestParam("ids") Long[] ids) {
		E3Result e3Result = itemService.deleteItem(ids);
		return e3Result;
	}

	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public E3Result instockItem(@RequestParam("ids") Long[] ids) {
		E3Result e3Result = itemService.instockItem(ids);
		return e3Result;
	}

	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public E3Result reshelfItem(@RequestParam("ids") Long[] ids) {
		E3Result e3Result = itemService.reshelfItem(ids);
		return e3Result;
	}
}
