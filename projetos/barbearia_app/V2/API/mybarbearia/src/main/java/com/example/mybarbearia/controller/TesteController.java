package com.example.mybarbearia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {
    @GetMapping
    public String helloWord() {
        return "Hello World, Vamos começar o projeto :)";
    }
}
