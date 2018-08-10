package com.e3.mapper;

import com.e3.pojo.TbContentCategory;
import com.e3.pojo.TbItemCat;

import java.util.List;

public interface TbContentCategoryMapper {
    List<TbContentCategory>  getContentCategoryList(long parentId);
    void insert(TbContentCategory tbContentCategory);
    TbContentCategory selectById(long parentId);
    void updateNameById(TbContentCategory tbContentCategory);
    void updateIsParentById(TbContentCategory tbContentCategory);
    void updateById(TbContentCategory tbContentCategory);
    void deleteById(long parentId);
    List<TbContentCategory> getListByParentId(long parentId);
}