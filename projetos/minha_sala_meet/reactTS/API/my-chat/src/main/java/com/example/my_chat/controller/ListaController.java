package com.example.my_chat.controller;

import com.example.my_chat.domain.grupo.DadosDetalhaGrupo;
import com.example.my_chat.domain.lista.DadosAlteraSituacaoLista;
import com.example.my_chat.domain.lista.DadosDetalhaLista;
import com.example.my_chat.domain.lista.DadosRegistraLista;
import com.example.my_chat.domain.lista.Lista;
import com.example.my_chat.domain.usuario.TipoUsuario;
import com.example.my_chat.infra.exception.ValidacaoException;
import com.example.my_chat.repository.GrupoRepository;
import com.example.my_chat.repository.ListaRepository;
import com.example.my_chat.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
public class ListaController {
    @Autowired
    private ListaRepository listaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GrupoRepository grupoRepository;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosRegistraLista dados) {
        var usuario = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.usuario_id());
        var grupo = grupoRepository.getReferenceById(Long.valueOf(dados.grupo_id()));

        if (usuario == null) {
            throw new ValidacaoException("Usuário ou Grupo inválidos");
        }

        var lista = new Lista(usuario, grupo, dados.cargo());
        listaRepository.save(lista);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharPorId(@PathVariable Long id) {
        var lista = listaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhaLista(lista));
    }

    @PutMapping
    @Transactional
    public ResponseEntity alteraSituacao(@RequestBody @Valid DadosAlteraSituacaoLista dados) {
        var dadoLista = listaRepository.getReferenceById(dados.id());
        dadoLista.alterarSituacao(dados.cargo());
        return ResponseEntity.ok().build();
    }
}
