package com.qiangzengy.eshop.cache.controller;

import com.qiangzengy.eshop.cache.entity.ProductInfo;
import com.qiangzengy.eshop.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @PostMapping("/saveLocalCache")
    public void saveLocalCache(@RequestBody ProductInfo productInfo){
        System.out.println("productInfo:"+productInfo.toString());
        cacheService.saveLocalCache(productInfo);
    }


    @GetMapping("/getLocalCache")
    public ProductInfo getLocalCache(Long id){
       return   cacheService.getLocalCache(id);
    }

}
