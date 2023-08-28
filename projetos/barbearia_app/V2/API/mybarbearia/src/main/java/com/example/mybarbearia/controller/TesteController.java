package com.example.mybarbearia.model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {
    @GetMapping
    public String holloWord() {
        return "Hello World, Vamos começar o projeto :)";
    }
}
