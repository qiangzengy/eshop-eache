package com.qiangzengy.eshop.cache.controller;

import com.qiangzengy.eshop.cache.entity.ProductInfo;
import com.qiangzengy.eshop.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @RequestMapping("/saveLocalCache")
    public void saveLocalCache(ProductInfo productInfo){
        cacheService.saveLocalCache(productInfo);
    }


    @RequestMapping("/getLocalCache")
    public ProductInfo getLocalCache(Long id){
       return   cacheService.getLocalCache(id);
    }


}
