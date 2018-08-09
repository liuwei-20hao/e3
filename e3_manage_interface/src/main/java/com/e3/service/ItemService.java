package com.e3.service;

import com.e3.pojo.E3Result;
import com.e3.pojo.EasyUIDataGridResult;
import com.e3.pojo.TbItem;

public interface ItemService {
    TbItem getTbItemById(long id);
    EasyUIDataGridResult getItemList(int page, int rows);
    E3Result addTtem(TbItem tbItem,String desc);
}
