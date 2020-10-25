package com.tl.eccommerceorderservice.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tl.eccommerceorderservice.result.Resp;
import com.tl.eccommerceorderservice.service.ProductOrderService;
import com.tl.eccommerceorderservice.service.impl.ProductClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {


    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    ProductClientImpl productClient;


    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object save(@RequestParam("user_id")int userId, @RequestParam("product_id") int productId){

//        return productOrderService.save(userId, productId);

        return Resp.ok(productClient.save(userId, productId)).code(200).msg("抢购成功");
    }


    /**
     * 测试hystrix 熔断机制
     * @return
     */
    public Resp saveOrderFail(int user_id, int product_id){
        return Resp.fail().code(-1).msg("活动太火爆了，请稍等再来");
    }


}
