package com.e3.cart.service.impl;

import com.e3.cart.service.CartService;
import com.e3.mapper.TbItemMapper;
import com.e3.pojo.E3Result;
import com.e3.pojo.JedisClient;
import com.e3.pojo.JsonUtils;
import com.e3.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    public E3Result addCart(Long userId,Long itemId, Integer num) {
        //向redis中添加购物车。
        //数据类型是hash key：用户id field：商品id value：商品信息
        //判断商品是否存在
        Boolean hexists = jedisClient.hexists("CART:" + userId, itemId + "");
        //如果存在数量相加
        if(hexists){
            String json = jedisClient.hget("CART:" + userId, itemId + "");
            TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
            item.setNum(item.getNum()+num);
            jedisClient.hset("CART:" + userId, itemId + "",JsonUtils.objectToJson(item));
            return E3Result.ok();
        }
        //如果不存在，根据商品id取商品信息
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
        //设置购物车数据量
        item.setNum(num);
        //取一张图片
        String image = item.getImage();
        if (StringUtils.isNotBlank(image)) {
            item.setImage(image.split(",")[0]);
        }
        //添加到购物车列表
        jedisClient.hset("CART:" + userId, itemId + "", JsonUtils.objectToJson(item));
        return E3Result.ok();
    }

    @Override
    public E3Result mergeCartList(Long userId, List<TbItem> tbItem) {
        for (TbItem item :tbItem) {
            addCart(userId,item.getId(),item.getNum());
        }
        return E3Result.ok();
    }

    @Override
    public List<TbItem> getCartList(Long userId) {
        List<String> hvals = jedisClient.hvals("CART:" + userId);
        List<TbItem> items = new ArrayList<>();
        for(String s:hvals){
            TbItem tbItem = JsonUtils.jsonToPojo(s,TbItem.class);
            items.add(tbItem);
        }
        return items;
    }

    @Override
    public void updateCartNum(long userId, long itemId, int num) {
        //从redis中取商品信息
        String json = jedisClient.hget("CART:" + userId, itemId + "");
        //更新商品数量
        TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
        tbItem.setNum(num);
        //写入redis
        jedisClient.hset("CART:" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
    }

    @Override
    public void deleteCartItem(long userId, long itemId) {
        // 删除购物车商品
        jedisClient.hdel("CART:" + userId, itemId + "");
    }

    @Override
    public void clearCartItem(long userId) {
        //删除购物车信息
        jedisClient.del("CART:" + userId);
    }
}
