package com.example.my_chat.controller;

import com.example.my_chat.domain.grupo.DadosInfoGrupoUser;
import com.example.my_chat.domain.membro.*;
import com.example.my_chat.domain.usuario.DadosDetalhamentoUser;
import com.example.my_chat.repository.MembroRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
public class MembroController {
    @Autowired
    private MembroRepository membroRepository;

    @Autowired
    private MembroService service;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosRegistraMembro dados) {
        service.addIntegranteMembro(dados);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharPorId(@PathVariable Long id) {
        var lista = membroRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhaMembro(lista));
    }

    @PutMapping
    @Transactional
    public ResponseEntity alteraSituacao(@RequestBody @Valid DadosAlteraSituacaoLista dados) {
        var dadoLista = membroRepository.getReferenceById(dados.id());
        dadoLista.alterarSituacao(dados.cargo());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<DadosDetalhamentoUser>> listarPorGrupo(@PathVariable Long id) {
        var listaMembros = membroRepository.findAllByGrupoId(id).stream().map(DadosDetalhamentoUser::new).toList();
        return ResponseEntity.ok(listaMembros);
    }

    @PostMapping("/del")
    @Transactional
    public ResponseEntity deletar(@RequestBody @Valid DadosDeletaMembroLista dados) {
        service.delintegrante(dados);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity sairGrupo(@PathVariable Long id) {
        membroRepository.deleteByUsuarioId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user")
    @Transactional
    public ResponseEntity detalharPorUserAndGrupo(@RequestBody @Valid DadosInfoGrupoUser dados) {
        Membro userMembro = membroRepository.getReferenceByUsuarioIdAndGrupoId(dados.user_id(), dados.grupo_id());
        return ResponseEntity.ok(new DadosDetalhaMembro(userMembro));
    }
}
