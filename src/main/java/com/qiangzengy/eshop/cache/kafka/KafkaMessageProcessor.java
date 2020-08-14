package com.qiangzengy.eshop.cache.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiangzengy.eshop.cache.entity.ProductInfo;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * kafka处理线程
 */
public class KafkaMessageProcessor implements Runnable {

    private KafkaStream kafkaStream;

    public KafkaMessageProcessor(KafkaStream kafkaStream) {
        this.kafkaStream = kafkaStream;
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
            String message = new String(it.next().message());
            JSONObject json= JSON.parseObject(message);
            //提取服务对应的标识
            String serviceid = json.getString("serviceid");
            //如果是商品信息服务
            if ("productInfoService".equals(serviceid)){
                processProductInfoChangeMessage(json);
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

}
