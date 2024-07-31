package com.example.my_chat.controller;

import com.example.my_chat.domain.grupo.DadosInfoGrupoUser;
import com.example.my_chat.domain.lista.*;
import com.example.my_chat.domain.usuario.DadosDetalhamentoUser;
import com.example.my_chat.domain.usuario.TipoUsuario;
import com.example.my_chat.infra.exception.ValidacaoException;
import com.example.my_chat.repository.GrupoRepository;
import com.example.my_chat.repository.ListaRepository;
import com.example.my_chat.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
@CrossOrigin(origins = "*")
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
        var user = listaRepository.getReferenceByUsuarioIdAndGrupoId(dados.user_id(), dados.grupo_id());
        var membro = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.membro_id());
        var grupo = grupoRepository.getReferenceById(dados.grupo_id());

        if (membro == null || user == null) {
            throw new ValidacaoException("Usuário ou Grupo inválidos");
        }

        if(user.getCargo() == TipoUsuario.ADMIN) {
            try {
                var lista = new Lista(membro, grupo, TipoUsuario.USER);
                listaRepository.save(lista);
                return ResponseEntity.ok().build();
            } catch (DataIntegrityViolationException ex) {
                throw new ValidacaoException("Não foi possivel adicionar Intergrante na lista pois ele já está na lista");
            }
        } else {
            throw new ValidacaoException("Você não tem permissão de excluir usuários");
        }
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

    @GetMapping("/group/{id}")
    public ResponseEntity<List<DadosDetalhamentoUser>> listarPorGrupo(@PathVariable Long id) {
        var listaMembros = listaRepository.findAllByGrupoId(id).stream().map(DadosDetalhamentoUser::new).toList();
        return ResponseEntity.ok(listaMembros);
    }

    @PostMapping("/del")
    @Transactional
    public ResponseEntity deletar(@RequestBody @Valid DadosDeletaMembroLista dados) {
        Lista membro = listaRepository.getReferenceByUsuarioIdAndGrupoId(dados.membro_id(), dados.grupo_id());

        Lista user = listaRepository.getReferenceByUsuarioIdAndGrupoId(dados.user_id(), dados.grupo_id());

        if(user.getCargo() == TipoUsuario.ADMIN) {
            listaRepository.delete(membro);
        } else {
            throw new ValidacaoException("Você não tem permissão de excluir usuários");
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user")
    @Transactional
    public ResponseEntity detalharPorUserAndGrupo(@RequestBody @Valid DadosInfoGrupoUser dados) {
        Lista userMembro = listaRepository.getReferenceByUsuarioIdAndGrupoId(dados.user_id(), dados.grupo_id());

        return ResponseEntity.ok(new DadosDetalhaLista(userMembro));
    }
}
