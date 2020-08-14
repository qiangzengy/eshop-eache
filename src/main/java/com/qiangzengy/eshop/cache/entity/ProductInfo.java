package com.qiangzengy.eshop.cache.entity;

  import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfo {

    private Long id;
    private String name;
    private BigDecimal price;


    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
