package com.qiangzengy.eshop.cache.service;

import com.qiangzengy.eshop.cache.entity.ProductInfo;

public interface CacheService {

    ProductInfo saveLocalCache(ProductInfo productInfo);
    ProductInfo getLocalCache(Long id);
    ProductInfo saveProductInfoToLocalCache(ProductInfo productInfo);
    void saveProductInfoToRedisCache(ProductInfo productInfo);


}
