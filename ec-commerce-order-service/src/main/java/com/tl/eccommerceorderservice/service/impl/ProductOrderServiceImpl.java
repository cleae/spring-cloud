package com.tl.eccommerceorderservice.service.impl;


import com.tl.eccommerceorderservice.domain.ProductOrder;
import com.tl.eccommerceorderservice.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {


//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Override
    public ProductOrder save(int userId, int productId) {

//        Object obj = restTemplate.getForObject("http://ec-commerce-commodity-service/api/v1/product/find?id="+productId, Object.class);
//
//        System.out.println(obj);
//
//        ProductOrder productOrder = new ProductOrder();
//        productOrder.setCreateTime(new Date());
//        productOrder.setUserId(userId);
//        productOrder.setTradeNo(UUID.randomUUID().toString());
//
//        return productOrder;


//        Map<String,Object> productMap = restTemplate.getForObject("http://product-service/api/v1/product/find?id="+productId, Map.class);

        //调用方式二
        ServiceInstance instance = loadBalancer.choose("ec-commerce-commodity-service");

        String url = String.format("http://%s:%s/api/v1/product/find?id="+productId, instance.getHost(),instance.getPort());
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> productMap = restTemplate.getForObject(url, Map.class);


        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(new Date());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setProductName(productMap.get("name").toString());
        productOrder.setPrice(Integer.parseInt(productMap.get("price").toString()));
        return productOrder;

    }
}
