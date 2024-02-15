package com.example.polls.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthoritiesController {

    @GetMapping("/admin")

    public String helloAdminController(){
        return "Admin access level";
    }
    @GetMapping("/user")

    public String helloUserController(){
        return "user access level";
    }
    @GetMapping("/")

    public String helloAnyOneController(){
        return "AnyOne access level";
    }

}
