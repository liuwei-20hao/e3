package com.e3.controller;

import com.e3.pojo.EasyUIDataGridResult;
import com.e3.pojo.EasyUITreeNode;
import com.e3.pojo.TbItem;
import com.e3.service.ItemCatService;
import com.e3.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemList(@RequestParam(name = "id",defaultValue = "0") long parentId) {
        //调用服务查询商品列表
		List<EasyUITreeNode> itemCatList = itemCatService.getItemCatList(parentId);
		return itemCatList;
    }
}
