package com.tl.eccommerceorderservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.tl.eccommerceorderservice.domain.ProductOrder;
import com.tl.eccommerceorderservice.service.ProductClient;
import com.tl.eccommerceorderservice.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * description: ProductClientImpl
 * created by lintan at 2020/10/22 17:25
 */
@Service
public class ProductClientImpl {

    @Autowired
    ProductClient productClient;


    public ProductOrder save(int userId, int productId) {
        System.out.println("userId:" + userId);
        if(productId==1)
            return null;//productId==1的时候正常返回

        /**
         * 通过FeignClient 调用远程接口
         */
        String product = productClient.findById(productId);

        System.err.println("?????" + product);
        JsonNode productMap = JsonUtils.str2JsonNode(product);
        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(new Date());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setProductName(productMap.get("name").toString());
        productOrder.setPrice(Integer.parseInt(productMap.get("price").toString()));
        return productOrder;

    }
}
