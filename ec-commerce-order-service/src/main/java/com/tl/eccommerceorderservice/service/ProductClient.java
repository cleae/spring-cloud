package com.tl.eccommerceorderservice.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tl.eccommerceorderservice.fallback.ProductClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description: ProductClient 商品服务客户端
 * created by lintan at 2020/10/22 17:03
 */
@FeignClient(value = "ec-commerce-commodity-service" , fallback = ProductClientFallBack.class)
public interface ProductClient {


    /**
     * 使用feign 调用服务名为ec-commerce-commodity-service 的服务下面的相应接口
     * @param id
     * @return
     */
    @GetMapping("/api/v1/product/find")
    String findById(@RequestParam(value = "id") int id );



}
