package com.e3.controller;

import com.e3.content.service.ContentCategoryService;
import com.e3.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentList(@RequestParam(name = "id", defaultValue = "0") long parentId) {
        List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
        return list;
    }

    /**
     * 添加分类节点
     */
    @RequestMapping(value = "/content/category/create", method = RequestMethod.POST)
    @ResponseBody
    public E3Result createContentCategory(Long parentId, String name)  throws Exception{
        //调用服务添加节点
        E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
        return e3Result;
    }

    @RequestMapping("/content/category/update")
    @ResponseBody
    public E3Result updateContentCategory(Long id, String name)  throws Exception{
        //调用服务添加节点
        E3Result e3Result = contentCategoryService.updateContentCategory(id, name);
        return e3Result;
    }

    @RequestMapping("/content/category/delete/")
    @ResponseBody
    public E3Result deleteContentCategory(Long id)  throws Exception{
        //调用服务添加节点
        E3Result e3Result = contentCategoryService.deleteContentCategory(id);
        return e3Result;
    }

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Integer page, Integer rows,long categoryId) {
        //调用服务查询内容列表
        EasyUIDataGridResult result = contentCategoryService.getContentList(page,rows,categoryId);
        return result;
    }

    @RequestMapping("/content/save")
    @ResponseBody
    public E3Result addTbContent(TbContent tbContent) {
        E3Result e3Result = contentCategoryService.addTbContent(tbContent);
        return e3Result;
    }

    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public E3Result updateTbContent(TbContent tbContent) {
        E3Result e3Result = contentCategoryService.updateById(tbContent);
        return e3Result;
    }

    @RequestMapping("/content/delete")
    @ResponseBody
    public E3Result deleteTbContent(long[] ids) {
        E3Result e3Result = contentCategoryService.deleteById(ids);
        return e3Result;
    }
}