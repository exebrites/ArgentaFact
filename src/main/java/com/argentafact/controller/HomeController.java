package com.argentafact.controller;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String hola() {
        return "Hola Mundo desde Spring Boot";
    }
}
