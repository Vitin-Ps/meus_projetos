package com.example.mybarbearia.controller;

import com.example.mybarbearia.model.carrinhodecompras.DadosListagemCarrinho;
import com.example.mybarbearia.model.carrinhodecompras.CarrinhoService;
import com.example.mybarbearia.model.carrinhodecompras.DadosCadastroCarrinho;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoDeComprasController {
    @Autowired
    CarrinhoService carrinhoService;

    @PostMapping
    @Transactional
    public ResponseEntity adicionarItem(@RequestBody @Valid DadosCadastroCarrinho dados) {
        var dadosCarrinho = carrinhoService.addNoCarrinho(dados);
        return ResponseEntity.ok(dadosCarrinho);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<DadosListagemCarrinho>> detalhar(@PathVariable Long id, Pageable pageable) {
        var page = carrinhoService.detalharCarrinho(id, pageable);
        return ResponseEntity.ok(page);
    }
    @DeleteMapping
    @Transactional
    public ResponseEntity removerItem(@RequestBody @Valid DadosCadastroCarrinho dados) {
        carrinhoService.retirarCarrinho(dados);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelarCarrinho(@PathVariable Long id) {
        carrinhoService.cancelarCarrinho(id);
        return ResponseEntity.noContent().build();
    }
}
