package com.example.my_chat.controller;

import com.example.my_chat.domain.mensagem.DadosDetalhaMensagem;
import com.example.my_chat.domain.mensagem.DadosRegistraMensagem;
import com.example.my_chat.domain.mensagem.Mensagem;
import com.example.my_chat.infra.exception.ValidacaoException;
import com.example.my_chat.repository.GrupoRepository;
import com.example.my_chat.repository.MensagemRepository;
import com.example.my_chat.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagem")
@CrossOrigin(origins = "*")
public class MensagemController {
    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosRegistraMensagem dados) {
        var usuario = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.usuario_id());
        var grupo = grupoRepository.getReferenceById(dados.grupo_id());

        if (usuario == null) {
            throw new ValidacaoException("Usuário ou Grupo inválidos");
        }

        var mensagem = new Mensagem(usuario, grupo, dados.mensagem(), dados.data());
        mensagemRepository.save(mensagem);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<DadosDetalhaMensagem>> listarPorId(@PathVariable Long id) {
        List<DadosDetalhaMensagem> listaDeMensagens = mensagemRepository.findAllByGrupoIdOrderByData(id)
                .stream().map(DadosDetalhaMensagem::new).toList();

        return ResponseEntity.ok(listaDeMensagens);
    }
}
