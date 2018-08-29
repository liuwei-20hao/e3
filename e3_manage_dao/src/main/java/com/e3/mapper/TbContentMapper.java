package com.e3.mapper;

import com.e3.pojo.TbContent;
import com.e3.pojo.TbContentCategory;

import java.util.List;

public interface TbContentMapper {
    List<TbContent> getContentListById(long contentId);
    int insert(TbContent tbContent);
    void updateById(TbContent tbContent);
    void deleteById(long id);
    TbContent getContentById(long id);
}