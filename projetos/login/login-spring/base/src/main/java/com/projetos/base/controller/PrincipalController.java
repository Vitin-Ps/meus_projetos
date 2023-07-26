package com.projetos.base.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PrincipalController {
    @GetMapping
    public String pagIndex() {
        return "index";
    }
}
