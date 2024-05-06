package com.rsl.springsecexamplev2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/controller")
public class Controller {

    @GetMapping("/admin")
    public String getAdmin(){
        return "ADMIN";
    }

    @GetMapping("/user")
    public String getClient(){
        return "USER";
    }

    @GetMapping("/any")
    public String getAny(){
        return "any";
    }
}
