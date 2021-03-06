package com.qiangzengy.eshop.cache.service;

import com.qiangzengy.eshop.cache.entity.ProductInfo;
import com.qiangzengy.eshop.cache.entity.ShopInfo;

public interface CacheService {

    ProductInfo saveLocalCache(ProductInfo productInfo);
    ProductInfo getLocalCache(Long id);
    ProductInfo saveProductInfoToLocalCache(ProductInfo productInfo);
    void saveProductInfoToRedisCache(ProductInfo productInfo);


    ShopInfo saveShopLocalCache(ShopInfo shopInfo);
    ShopInfo getShopLocalCache(Long id);
    ShopInfo saveShopInfoToLocalCache(ShopInfo shopInfo);
    void saveShopInfoToRedisCache(ShopInfo shopInfo);




}
