package com.e3.sso.service;

import com.e3.pojo.E3Result;

public interface LoginService {
    E3Result login(String username,String password);
}
