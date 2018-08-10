package com.e3.service;

import com.e3.pojo.E3Result;
import com.e3.pojo.EasyUIDataGridResult;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbItemDesc;

public interface ItemService {
    TbItem getTbItemById(long id);
    TbItemDesc getTbItemDescById(long id);
    EasyUIDataGridResult getItemList(int page, int rows);
    E3Result addTtem(TbItem tbItem,String desc);
    E3Result updateTtem(TbItem tbItem,String desc);
    E3Result deleteItem(Long[] params);
    E3Result instockItem(Long[] params);
    E3Result reshelfItem(Long[] params);
}
