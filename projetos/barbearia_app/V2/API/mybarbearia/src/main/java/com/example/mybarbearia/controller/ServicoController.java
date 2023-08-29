package com.example.mybarbearia.controller;

import com.example.mybarbearia.model.servico.*;
import com.example.mybarbearia.repository.ServicoRepository;
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
@RequestMapping("/servico")
public class ServicoController {
    @Autowired
    private ServicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroServico dados, UriComponentsBuilder componentsBuilder) {
        var servico = new Servico(dados);
        repository.save(servico);

        var uri = componentsBuilder.path("/servico/{id}").buildAndExpand(servico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemServico(servico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemServico>> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable pageable) {
        var page = repository.findByAtivoTrue(pageable).map(DadosListagemServico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizaServico dados) {
        var servico = repository.getReferenceById(dados.id());
        System.out.println(dados);
        servico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosListagemServico(servico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity apagarLogico(@PathVariable Long id) {
        var servico = repository.getReferenceById(id);
        servico.apagarLogico();
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
        var servico = repository.getReferenceByIdAndAtivoTrue(id);
        return ResponseEntity.ok(new DadosListagemServico(servico));
    }
}
