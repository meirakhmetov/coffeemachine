package com.coffee.machine.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Operation(summary = "Тестовый GET запрос", description = "Просто проверяем, работает ли Swagger")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Swagger!";
    }
}
