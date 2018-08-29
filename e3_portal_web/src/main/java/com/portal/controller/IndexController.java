package com.portal.controller;

import com.e3.content.service.ContentCategoryService;
import com.e3.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ContentCategoryService contentCategoryService;
    @RequestMapping("/index")
    public String showIndex(Model model){
        List<TbContent> ad1List = contentCategoryService.getContentListById(89);
        model.addAttribute("ad1List",ad1List);
        return "index";
    }
}
