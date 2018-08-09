package com.e3.mapper;

import com.e3.pojo.TbItemCat;
import com.e3.pojo.TbUser;

import java.util.List;
import java.util.Map;

public interface TbUserMapper {
    List<TbUser>  selectUserByUserName(String username);
    List<TbUser>  selectUserByPhone(String phone);
    List<TbUser>  selectUserByEmail(String email);
    void insert(TbUser tbUser);
}