package com.e3.mapper;

import com.e3.pojo.EasyUIDataGridResult;
import com.e3.pojo.TbItem;

import java.util.List;

public interface TbItemMapper {
    TbItem selectByPrimaryKey(Long id);
    public List<TbItem> selectTbItem();
    void insert(TbItem tbItem);
    void updateItem(TbItem tbItem);
    void deleteById(long id);
}