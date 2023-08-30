package com.example.mybarbearia.controller;

import com.example.mybarbearia.model.carrinhodecompras.FuncionalidadesDoCarrinho;
import com.example.mybarbearia.model.carrinhodecompras.DadosCadastroCarrinho;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoDeComprasController {
    @Autowired
    FuncionalidadesDoCarrinho fCarrinho;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCarrinho dados) {
        System.out.println(dados.idCliente());
        System.out.println(dados.idProduto());
        fCarrinho.addNoCarrinho(dados);
        return ResponseEntity.ok().build();
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    public ResponseEntity deletar(@PathVariable Long id) {
//        fCarrinho.finalizarCarrinho(id);
//        return ResponseEntity.ok("Carrinho limpo");
//    }
}
