package com.rentalhive.rentalhive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MyController {

    @GetMapping("/rentalhive/")
    public String home() {

        return "Hello from MyController!";
    }
}
