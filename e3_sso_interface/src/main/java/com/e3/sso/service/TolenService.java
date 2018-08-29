package com.e3.sso.service;

import com.e3.pojo.E3Result;

public interface TolenService {
    E3Result getUserByToken(String token);
}
