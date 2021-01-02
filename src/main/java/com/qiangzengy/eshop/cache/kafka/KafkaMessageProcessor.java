package com.qiangzengy.eshop.cache.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiangzengy.eshop.cache.entity.ProductInfo;
import com.qiangzengy.eshop.cache.entity.ShopInfo;
import com.qiangzengy.eshop.cache.service.CacheService;
import com.qiangzengy.eshop.cache.spring.SpringContext;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * kafka处理线程
 */
@SuppressWarnings("rawtypes")
@Slf4j
public class KafkaMessageProcessor implements Runnable {

    private KafkaStream kafkaStream;

    private CacheService cacheService;

    @Autowired
    private CacheService cacheService1;

    public KafkaMessageProcessor(KafkaStream kafkaStream) {

        this.kafkaStream = kafkaStream;
        this.cacheService=cacheService1;
        //this.cacheService=(CacheService)new SpringContext().getApplicationContext().getBean("cacheService");
    }

    @Override
    public void run() {
        ConsumerIterator it = kafkaStream.iterator();
        while (it.hasNext()) {
            String message = new String((byte[]) it.next().message());
            log.info("===========>,message:{}",message);
            JSONObject json= JSON.parseObject(message);
            //提取服务对应的标识
            String serviceId = json.getString("serviceId");
            //如果是商品信息服务
            if ("productInfoService".equals(serviceId)){
                processProductInfoChangeMessage(json);
            }else if("shopInfoService".equals(serviceId)) {
                processShopInfoChangeMessage(json);
            }
        }
    }

    /**
     * 处理商品变更的消息
     * @param json
     */
    private void processProductInfoChangeMessage(JSONObject json) {
        //提取出商品id
        Long productId = json.getLong("productId");

        // 调用商品信息服务的接口
        // 直接用注释模拟：getProductInfo?productId=1，传递过去
        // 商品信息服务，一般来说就会去查询数据库，去获取productId=1的商品信息，然后返回回来
        //模拟商品信息
        String productInfoJSON = "{\"id\": 5, \"name\": \"iphone7手机\", " +
                "\"price\": 5599, \"pictureList\":\"a.jpg,b.jpg\", " +
                "\"specification\": \"iphone7的规格\", \"service\": \"iphone7的售后服务\"," +
                " \"color\": \"红色,白色,黑色\", \"size\": \"5.5\", \"shopId\": 1, " +
                "\"modifiedTime\": \"2017-01-01 12:00:00\"}";

        ProductInfo productInfo=JSONObject.parseObject(productInfoJSON,ProductInfo.class);

    }

    /**
     * 处理店铺信息变更的消息
     * @param messageJSONObject
     */
    private void processShopInfoChangeMessage(JSONObject messageJSONObject) {
        // 提取出商品id
        //Long productId = messageJSONObject.getLong("productId");
        Long shopId = messageJSONObject.getLong("shopId");

        // 调用商品信息服务的接口
        // 直接用注释模拟：getProductInfo?productId=1，传递过去
        // 商品信息服务，一般来说就会去查询数据库，去获取productId=1的商品信息，然后返回回来

        // 龙果有分布式事务的课程，主要讲解的分布式事务几种解决方案，里面也涉及到了一些mq，或者其他的一些技术，但是那些技术都是浅浅的给你搭建一下，使用
        // 你从一个课程里，还是学到的是里面围绕的讲解的一些核心的知识
        // 缓存架构：高并发、高性能、海量数据，等场景

        String shopInfoJSON = "{\"id\": 1, \"name\": \"小王的手机店\", \"level\": 5, \"goodCommentRate\":0.99}";
        ShopInfo shopInfo = JSONObject.parseObject(shopInfoJSON, ShopInfo.class);
        cacheService.saveShopInfoToLocalCache(shopInfo);
        System.out.println("===================获取刚保存到本地缓存的店铺信息：" + cacheService.getShopLocalCache(shopId));
        cacheService.saveShopInfoToRedisCache(shopInfo);

    }

}
