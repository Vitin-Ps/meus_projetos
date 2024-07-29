package com.example.my_chat.controller;

import com.example.my_chat.domain.grupo.DadosDetalhaGrupo;
import com.example.my_chat.domain.grupo.DadosRegistroGrupo;
import com.example.my_chat.domain.grupo.Grupo;
import com.example.my_chat.repository.GrupoRepository;
import com.example.my_chat.repository.ListaRepository;
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
    private GrupoRepository grupoRepository;

    @Autowired
    private ListaRepository listaRepository;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosRegistroGrupo dados) {
        var grupo = new Grupo(dados.uuid(), dados.nome());
        grupoRepository.save(grupo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<DadosDetalhaGrupo>> listarPorId(@PathVariable Long id) {
        List<DadosDetalhaGrupo> listaDeGrupos = listaRepository.findAllByUsuarioId(id).stream().map(DadosDetalhaGrupo::new).toList();

        return ResponseEntity.ok(listaDeGrupos);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharPorId(@PathVariable Long id) {
        var grupo = grupoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhaGrupo(grupo));
    }
}
