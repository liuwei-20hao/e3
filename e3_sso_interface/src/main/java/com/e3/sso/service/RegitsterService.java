package com.e3.sso.service;

import com.e3.pojo.E3Result;
import com.e3.pojo.TbUser;

public interface RegitsterService {
    E3Result checkData(String param, Integer type);
    E3Result register(TbUser user);
}
