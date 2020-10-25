package com.tl.eccommerceorderservice.fallback;

import com.tl.eccommerceorderservice.service.ProductClient;
import org.springframework.stereotype.Component;

/**
 * 针对商品服务，出错降级处理
 */
@Component
public class ProductClientFallBack implements ProductClient {


    @Override
    public String findById(int id) {
        System.out.println("feign 调用 ec-commerce-commodity-service findbyid 异常");
        return null;
    }
}
