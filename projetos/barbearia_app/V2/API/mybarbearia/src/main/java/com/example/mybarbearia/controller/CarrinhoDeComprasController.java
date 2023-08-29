package com.example.mybarbearia.controller;

import com.example.mybarbearia.model.carrinhodecompras.AdicionarNoCarrinho;
import com.example.mybarbearia.model.carrinhodecompras.DadosCadastroCarrinho;
import com.example.mybarbearia.repository.CarrinhoDeComprasRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoDeComprasController {
    @Autowired
    AdicionarNoCarrinho add;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCarrinho dados) {
        return ResponseEntity.ok().build();
    }
}
