package com.example.mybarbearia.controller;

import com.example.mybarbearia.model.carrinhodecompras.CarrinhoDeCompras;
import com.example.mybarbearia.model.carrinhodecompras.DadosListagemCarrinho;
import com.example.mybarbearia.model.carrinhodecompras.FuncionalidadesDoCarrinho;
import com.example.mybarbearia.model.carrinhodecompras.DadosCadastroCarrinho;
import com.example.mybarbearia.repository.CarrinhoDeComprasRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoDeComprasController {
    @Autowired
    FuncionalidadesDoCarrinho fCarrinho;

    @Autowired
    CarrinhoDeComprasRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity adicionarItem(@RequestBody @Valid DadosCadastroCarrinho dados) {
        fCarrinho.addNoCarrinho(dados);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<DadosListagemCarrinho>> detalhar(@PathVariable Long id, Pageable pageable) {
        var page = repository.findByClienteId(id, pageable).map(DadosListagemCarrinho::new);
        return ResponseEntity.ok(page);
    }
    @DeleteMapping
    @Transactional
    public ResponseEntity removerItem(@RequestBody @Valid DadosCadastroCarrinho dados) {
        fCarrinho.retirarCarrinho(dados);
        return ResponseEntity.ok("Carrinho limpo");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelarCarrinho(@PathVariable Long id) {
        fCarrinho.cancelarCarrinho(id);
        return ResponseEntity.ok("Carrinho limpo");
    }
}
