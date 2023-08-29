package com.example.mybarbearia.controller;

import com.example.mybarbearia.model.cliente.Cliente;
import com.example.mybarbearia.model.cliente.DadosAtualizaCliente;
import com.example.mybarbearia.model.cliente.DadosListagemCliente;
import com.example.mybarbearia.repository.ClienteRepository;
import com.example.mybarbearia.model.cliente.DadosCadastroCliente;
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
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrat(@RequestBody @Valid DadosCadastroCliente dados, UriComponentsBuilder componentsBuilder) {
        var cliente = new Cliente(dados);
        repository.save(cliente);

        var uri = componentsBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemCliente(cliente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCliente>> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"})Pageable pageable) {
        var page = repository.findByAtivoTrue(pageable).map(DadosListagemCliente::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity alterar(@RequestBody @Valid DadosAtualizaCliente dados) {
        var cliente = repository.getReferenceById(dados.id());
        cliente.atualizaInformacoes(dados);
        return  ResponseEntity.ok(new DadosListagemCliente(cliente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity apagarLogico(@PathVariable Long id) {
        var cliente = repository.getReferenceByIdAndAtivoTrue(id);
        cliente.apagaLogico();
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
        var cliente = repository.getReferenceByIdAndAtivoTrue(id);
        return ResponseEntity.ok(new DadosListagemCliente(cliente));
    }
}
