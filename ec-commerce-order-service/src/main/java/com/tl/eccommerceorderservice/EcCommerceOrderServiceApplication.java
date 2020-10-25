package com.tl.eccommerceorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker  //熔断相关
public class EcCommerceOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcCommerceOrderServiceApplication.class, args);
    }


    @Bean
    @LoadBalanced//客户端实现负载均衡
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }

}
