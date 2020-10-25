package com.tl.commidity.eccommercecommodityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
/**
 * By having spring-cloud-starter-netflix-eureka-client on the classpath,
 * your application automatically registers with the Eureka Server
 *
 * 只要把依赖加载到类路径下面，应用用自动注册到eureka server 不用再加@EnableEurekaClient 注解
 */
//@EnableEurekaClient
public class EcCommerceCommodityServiceApplication {

        public static void main(String[] args) {
            SpringApplication.run(EcCommerceCommodityServiceApplication.class, args);
        }

}
