package com.e3.service.impl;

import com.e3.mapper.TbItemDescMapper;
import com.e3.mapper.TbItemMapper;
import com.e3.pojo.*;
import com.e3.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Override
    public TbItem getTbItemById(long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public TbItemDesc getTbItemDescById(long id) {
        return tbItemDescMapper.selectById(id);
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        List<TbItem> tbItemslist = tbItemMapper.selectTbItem();
        //创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(tbItemslist);
        //取分页结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItemslist);
        //取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public E3Result addTtem(TbItem tbItem, String desc) {
        //生成商品id
        long itemId = IDUtils.genItemId();
        //补全item的属性
        tbItem.setId(itemId);
        //1-正常，2-下架，3-删除
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        //向商品表插入数据
        tbItemMapper.insert(tbItem);
        //创建一个商品描述表对应的pojo对象。
        TbItemDesc itemDesc = new TbItemDesc();
        //补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        tbItemDescMapper.insert(itemDesc);
        //返回成功
        return E3Result.ok();
    }

    @Override
    public E3Result updateTtem(TbItem tbItem, String desc) {
        tbItem.setUpdated(new Date());
        //1-正常，2-下架，3-删除
        tbItem.setStatus((byte) 1);
        tbItemMapper.updateItem(tbItem);
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(new Date());
        tbItemDescMapper.update(tbItemDesc);
        return E3Result.ok();
    }

    @Override
    public E3Result reshelfItem(Long [] params) {
        for(long id :params){
            TbItem tbItem = new TbItem();
            tbItem.setStatus((byte) 1);
            tbItem.setId(id);
            tbItemMapper.updateItem(tbItem);
        }
        return E3Result.ok();
    }

    @Override
    public E3Result instockItem(Long [] params) {
        for(long id :params){
            TbItem tbItem = new TbItem();
            tbItem.setStatus((byte) 2);
            tbItem.setId(id);
            tbItemMapper.updateItem(tbItem);
        }
        return E3Result.ok();
    }

    @Override
    public E3Result deleteItem(Long [] params) {
        for(long id :params){
            TbItem tbItem = new TbItem();
            tbItem.setStatus((byte) 3);
            tbItem.setId(id);
            tbItemMapper.updateItem(tbItem);
        }
        return E3Result.ok();
    }

    @Test
    public void test(){
        //1、获得连接对象
        Jedis jedis = new Jedis("132.232.17.190", 6379);

        //2、获得数据
        String username = jedis.get("str1");
        System.out.println(username);

        //3、存储
        jedis.set("addr", "北京");
        System.out.println(jedis.get("addr"));
    }

    //通过jedis的pool获得jedis连接对象
    @Test
    public void test2(){
        //0、创建池子的配置对象
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(30);//最大闲置个数
        poolConfig.setMinIdle(10);//最小闲置个数
        poolConfig.setMaxTotal(50);//最大连接数

        //1、创建一个redis的连接池
        JedisPool pool = new JedisPool(poolConfig, "132.232.17.190", 6379);

        //2、从池子中获取redis的连接资源
        Jedis jedis = pool.getResource();

        //3、操作数据库
        jedis.set("xxx","yyyy");
        System.out.println(jedis.get("xxx"));

        //4、关闭资源
        jedis.close();
        pool.close();

    }
}
