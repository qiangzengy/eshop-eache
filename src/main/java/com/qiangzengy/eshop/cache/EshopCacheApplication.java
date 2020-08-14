package com.qiangzengy.eshop.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * （1）两种服务会发送来数据变更消息：商品信息服务，商品店铺信息服务，
 * 每个消息都包含服务名以及商品id
 *
 * （2）接收到消息之后，根据商品id到对应的服务拉取数据，这一步，
 * 我们采取简化的模拟方式，就是在代码里面写死，会获取到什么数据，不去实际再写其他的服务去调用了
 *
 * （3）商品信息：id，名称，价格，图片列表，商品规格，售后信息，颜色，尺寸
 *
 * （4）商品店铺信息：其他维度，用这个维度模拟出来缓存数据维度化拆分，
 * id，店铺名称，店铺等级，店铺好评率
 *
 * （5）分别拉取到了数据之后，将数据组织成json串，然后分别存储到ehcache中，
 * 和redis缓存中
 */
@EnableSwagger2
@SpringBootApplication
public class EshopCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshopCacheApplication.class, args);
    }

}
