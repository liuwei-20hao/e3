package com.e3.cart.service;

import com.e3.pojo.E3Result;
import com.e3.pojo.TbItem;

import java.util.List;

public interface CartService {
    E3Result addCart(Long userId ,Long itemId, Integer num);
    E3Result mergeCartList(Long userId, List<TbItem> tbItem);
    List<TbItem> getCartList(Long userId);
    void updateCartNum(long userId, long itemId, int num);
    void deleteCartItem(long userId, long itemId);
    void clearCartItem(long userId);
}
