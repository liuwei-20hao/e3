package com.e3.service.impl;

import com.e3.mapper.TbItemMapper;
import com.e3.pojo.TbItem;
import com.e3.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    public TbItem getTbItemById(long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }
}
