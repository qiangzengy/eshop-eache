package com.qiangzengy.eshop.cache.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiangzengy.eshop.cache.entity.ProductInfo;
import com.qiangzengy.eshop.cache.service.CacheService;
import com.qiangzengy.eshop.cache.utils.RedisCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;



@Service
public class CacheServiceImpl implements CacheService {

    public static final String CACHE_NAME = "local";

    @Autowired
    private RedisCluster jedisCluster;

    /**
     * 将商品信息缓存到本地
     * @param productInfo
     */
    @Override
    @CachePut(value = CACHE_NAME, key = "'key_'+#productInfo.getId()")
    public ProductInfo saveLocalCache(ProductInfo productInfo) {

        return productInfo;

    }

    /**
     * 从本地获取商品信息
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = CACHE_NAME, key = "'key_'+#id")
    public ProductInfo getLocalCache(Long id) {
        return null;
    }

    /**
     * 将商品信息缓存到ehcache
     * @param productInfo
     * @return
     */
    @Override
    @CachePut(value = CACHE_NAME, key = "'product_info_'+#productInfo.getId()")
    public ProductInfo saveProductInfoToLocalCache(ProductInfo productInfo) {
        return productInfo;
    }

    /**
     * 将商品信息缓存到redis
     * @param productInfo
     */
    @Override
    public void saveProductInfoToRedisCache(ProductInfo productInfo) {
        jedisCluster.instance().set("product_info_" + productInfo.getId(), JSON.toJSONString(productInfo));
    }
}
