package com.e3.service;

import com.e3.pojo.EasyUITreeNode;
import com.e3.pojo.TbItemCat;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode>  getItemCatList(long parentId);
}
