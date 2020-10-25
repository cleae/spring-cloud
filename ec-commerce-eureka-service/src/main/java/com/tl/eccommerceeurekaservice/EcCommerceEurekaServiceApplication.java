package com.tl.eccommerceeurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EcCommerceEurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcCommerceEurekaServiceApplication.class, args);
    }

}
