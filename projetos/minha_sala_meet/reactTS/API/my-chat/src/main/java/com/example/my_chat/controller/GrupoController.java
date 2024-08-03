package com.example.my_chat.controller;

import com.example.my_chat.domain.grupo.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@CrossOrigin(origins = "*")
public class GrupoController {

    @Autowired
    private GrupoService service;
    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosRegistroGrupo dados) {
        DadosDetalhaGrupo grupo = service.cadastrarGrupo(dados);
        return ResponseEntity.ok(grupo);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<DadosDetalhaGrupo>> listarPorId(@PathVariable Long id) {
        List<DadosDetalhaGrupo> listaDeGrupos = service.listarPorId(id);
        return ResponseEntity.ok(listaDeGrupos);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharPorId(@PathVariable Long id) {
        Grupo grupo = service.detalharPorId(id);
        return ResponseEntity.ok(new DadosDetalhaGrupo(grupo));
    }

    @PostMapping("/del")
    @Transactional
    public ResponseEntity deletar(@RequestBody @Valid DadosInfoGrupoUser dados) {
        service.deletarGrupo(dados);
        return ResponseEntity.noContent().build();
    }
}
