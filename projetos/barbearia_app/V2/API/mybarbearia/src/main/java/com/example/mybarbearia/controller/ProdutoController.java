package com.example.mybarbearia.controller;

import com.example.mybarbearia.model.produto.*;
import com.example.mybarbearia.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    @Autowired
    private ProdutoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroProduto dados, UriComponentsBuilder componentsBuilder) {
        var produto = new Produto(dados);
        repository.save(produto);

        var uri = componentsBuilder.path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemProduto(produto));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemProduto>> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"})Pageable pageable) {
        var page = repository.findAllByAtivoTrue(pageable).map(DadosListagemProduto::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizaProduto dados) {
        var produto = repository.getReferenceById(dados.id());
        produto.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosListagemProduto(produto));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity apagarLogico(@PathVariable Long id) {
        var produto = repository.getReferenceById(id);
        System.out.printf(produto.getNome());
        produto.excluirLogico();
        System.out.println(produto.getAtivo());
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/apagar/{id}")
    @Transactional
    public ResponseEntity apagarDefinitivo(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var produto = repository.getReferenceByIdAndAtivoTrue(id);
        return ResponseEntity.ok(new DadosListagemProduto(produto));
    }

}
