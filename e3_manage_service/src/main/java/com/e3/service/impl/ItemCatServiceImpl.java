package com.e3.service.impl;

import com.e3.mapper.TbItemCatMapper;
import com.e3.pojo.EasyUITreeNode;
import com.e3.pojo.TbItemCat;
import com.e3.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        List<TbItemCat> itemCatList = tbItemCatMapper.getItemCatList(parentId);
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for (TbItemCat itemCat: itemCatList){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(itemCat.getId());
            node.setText(itemCat.getName());
            node.setState(itemCat.getIsParent()?"closed":"open");
            nodeList.add(node);
        }
        return nodeList;
    }
}
