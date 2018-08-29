package com.e3.content.service;

import com.e3.pojo.*;

import java.util.List;

public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCategoryList(long parentId);
    E3Result addContentCategory(long parentId,String name);
    E3Result updateContentCategory(long parentId,String name);
    E3Result deleteContentCategory(long id);
    EasyUIDataGridResult getContentList(Integer page, Integer rows, long categoryId);
    public E3Result addTbContent(TbContent tbContent);
    E3Result updateById(TbContent tbContent);
    E3Result deleteById(long[] id);
    List<TbContent> getContentListById(long categoryId);
}
