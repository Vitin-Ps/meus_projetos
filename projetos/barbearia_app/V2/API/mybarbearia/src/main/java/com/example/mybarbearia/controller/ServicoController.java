package com.example.mybarbearia.controller;

import com.example.mybarbearia.model.servico.DadosCadastroServico;
import com.example.mybarbearia.model.servico.ServicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servico")
public class Servico {
    @Autowired
    private ServicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroServico dados) {
        return ResponseEntity.ok("Serviço cadastrado com Sucesso");
    }
}
