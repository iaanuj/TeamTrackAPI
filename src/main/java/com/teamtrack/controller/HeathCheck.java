package com.teamtrack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeathCheck {
    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }
}
