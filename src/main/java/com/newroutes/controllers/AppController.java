package com.newroutes.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AppController {

    @GetMapping("/")
    public String home(){
        return "Newroutes home";
    }

    @GetMapping("/healthz")
    public void healthz(){
        log.info("Newroutes health check");
    }
}
