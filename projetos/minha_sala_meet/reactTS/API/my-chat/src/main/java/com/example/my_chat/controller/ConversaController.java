package com.example.my_chat.controller;

import com.example.my_chat.domain.conversa.ConversaService;
import com.example.my_chat.domain.conversa.DadosDetalhaConversaTipos;
import com.example.my_chat.domain.conversa.DadosInfoConversaUser;
import com.example.my_chat.domain.grupo.*;
import com.example.my_chat.domain.privado.DadosRegistroPrivado;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ConversaController {

    @Autowired
    private ConversaService conversaService;

    @Autowired
    private GrupoService grupoService;

    // ------------ CONVERSA -------------

    @GetMapping("/user/{id}")
    public ResponseEntity<List<DadosDetalhaConversaTipos>> cadastrar(@PathVariable Long id) {
        List<DadosDetalhaConversaTipos> listaConversas = conversaService.RetornaListaConversaPorUser(id);
        return ResponseEntity.ok(listaConversas);
    }

    @PostMapping
    public ResponseEntity detalarConversaPorId(@RequestBody @Valid DadosInfoConversaUser dados) {
        DadosDetalhaConversaTipos conversa = conversaService.detalharConversaPorId(dados);
        return ResponseEntity.ok(conversa);
    }

    @DeleteMapping("/delall")
    @Transactional
    public ResponseEntity deletarTudo() {
        conversaService.deletarTodasMensagens();
        return ResponseEntity.noContent().build();
    }

    // ------------ GRUPO -------------

    @PostMapping("/group")
    public ResponseEntity cadastrar(@RequestBody @Valid DadosRegistroGrupo dados) {
        DadosDetalhaConversaTipos grupo = grupoService.cadastrarGrupo(dados);
        return ResponseEntity.ok(grupo);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity detalharPorId(@PathVariable Long id) {
        Grupo grupo = grupoService.detalharPorId(id);
        return ResponseEntity.ok(new DadosDetalhaGrupo(grupo));
    }

    @PostMapping("/group/del")
    @Transactional
    public ResponseEntity deletar(@RequestBody @Valid DadosInfoGrupoUser dados) {
        grupoService.deletarGrupo(dados);
        return ResponseEntity.noContent().build();
    }

    // ------------ PRIVADO -------------

    @PostMapping("/privateuser")
    @Transactional
    public ResponseEntity cadastrarConversaPrivada(@RequestBody @Valid DadosRegistroPrivado dados) {
        conversaService.cadastraPrivado(dados);
        return ResponseEntity.ok().build();
    }


}
