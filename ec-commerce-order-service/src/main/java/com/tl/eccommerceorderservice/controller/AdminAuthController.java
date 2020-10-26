package com.tl.eccommerceorderservice.controller;

import com.tl.eccommerceorderservice.result.Resp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: AdminAuthController
 * created by lintan at 2020/10/26 17:46
 */
@RestController
@RequestMapping("/user")
public class AdminAuthController {

    @GetMapping("/hello")
    public Resp hello(@RequestParam("name")int name){
        return Resp.ok().code(200).msg("hello! "+name);
    }
}
