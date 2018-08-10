package com.e3.content.service;

import com.e3.pojo.E3Result;
import com.e3.pojo.EasyUITreeNode;
import com.e3.pojo.TbContentCategory;

import java.util.List;

public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCategoryList(long parentId);
    E3Result addContentCategory(long parentId,String name);
    E3Result updateContentCategory(long parentId,String name);
    E3Result deleteContentCategory(long id);
}
