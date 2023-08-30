package com.example.mybarbearia.controller;

import com.example.mybarbearia.model.estoque.DadosAtualizaEstoque;
import com.example.mybarbearia.model.estoque.DadosListagemEstoque;
import com.example.mybarbearia.repository.EstoqueRepository;
import com.example.mybarbearia.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {
    @Autowired
    EstoqueRepository repository;

    @GetMapping
    public ResponseEntity<Page<DadosListagemEstoque>> listar(@PageableDefault Pageable pageable) {
        var page = repository.findAll(pageable).map(DadosListagemEstoque::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity addQuantidade(@RequestBody @Valid DadosAtualizaEstoque dados) {
        var estoque = repository.getReferenceByProdutoId(dados.idProduto());
        System.out.println(estoque.getProduto().getNome());
        System.out.println("AQUI " + dados.alterarQuantidade());
        estoque.alterarQuantidade(dados);
        return ResponseEntity.ok(new DadosListagemEstoque(estoque));
    }

}
