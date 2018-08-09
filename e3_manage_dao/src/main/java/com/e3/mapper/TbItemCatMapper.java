package com.e3.mapper;

import com.e3.pojo.EasyUITreeNode;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbItemCat;

import java.util.List;

public interface TbItemCatMapper {
    List<TbItemCat>  getItemCatList(long parentId);
}