package com.qiangzengy.eshop.cache.entity;

  import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfo {

    private Long id;
    private String name;
    private BigDecimal price;
    private String pictureList;
    private String specification;
    private String service;
    private String color;
    private String size;
    private Long shopId;
    private String modifiedTime;


    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
