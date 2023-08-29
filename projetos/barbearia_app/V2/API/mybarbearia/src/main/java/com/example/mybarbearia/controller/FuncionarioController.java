package com.example.mybarbearia.controller;

import com.example.mybarbearia.model.funcionario.DadosAtualizaFuncionario;
import com.example.mybarbearia.model.funcionario.DadosCadastroFuncionario;
import com.example.mybarbearia.model.funcionario.DadosListagemFuncionario;
import com.example.mybarbearia.model.funcionario.Funcionario;
import com.example.mybarbearia.repository.FuncionarioRepository;
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
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroFuncionario dados, UriComponentsBuilder componentsBuilder) {
        var funcionario = new Funcionario(dados);
        repository.save(funcionario);

        var uri = componentsBuilder.path("/funcionario/{id}").buildAndExpand(funcionario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemFuncionario(funcionario));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemFuncionario>> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"})Pageable pageable) {
        var page = repository.findByAtivoTrue(pageable).map(DadosListagemFuncionario::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity alterar(@RequestBody @Valid DadosAtualizaFuncionario dados) {
        var funcionario = repository.getReferenceByIdAndAtivoTrue(dados.id());
        funcionario.atualizaInformacoes(dados);
        return ResponseEntity.ok(new DadosListagemFuncionario(funcionario));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity apagarLogico(@PathVariable Long id) {
        var funcionario = repository.getReferenceById(id);
        funcionario.apagarLogico();
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
        var funcionario = repository.getReferenceByIdAndAtivoTrue(id);
        return ResponseEntity.ok(new DadosListagemFuncionario(funcionario));
    }

}
