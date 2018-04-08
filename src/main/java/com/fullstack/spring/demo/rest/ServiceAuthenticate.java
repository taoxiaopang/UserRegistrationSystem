package com.fullstack.spring.demo.rest;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceAuthenticate {
    
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

}
