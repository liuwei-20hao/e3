package com.e3.mapper;

import java.util.List;

import com.e3.pojo.TbItem;
import com.e3.pojo.TbItemExample;
import org.apache.ibatis.annotations.Param;

public interface TbItemMapper {
    TbItem selectByPrimaryKey(Long id);
}