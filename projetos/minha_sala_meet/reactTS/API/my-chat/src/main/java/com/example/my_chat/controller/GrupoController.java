package com.example.my_chat.controller;

import com.example.my_chat.domain.grupo.DadosDetalhaGrupo;
import com.example.my_chat.domain.grupo.DadosInfoGrupoUser;
import com.example.my_chat.domain.grupo.DadosRegistroGrupo;
import com.example.my_chat.domain.grupo.Grupo;
import com.example.my_chat.domain.lista.Lista;
import com.example.my_chat.domain.usuario.TipoUsuario;
import com.example.my_chat.infra.exception.ValidacaoException;
import com.example.my_chat.repository.GrupoRepository;
import com.example.my_chat.repository.ListaRepository;
import com.example.my_chat.repository.MensagemRepository;
import com.example.my_chat.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosRegistroGrupo dados) {
        Grupo grupo = new Grupo(dados.uuid(), dados.nome());
        grupo = grupoRepository.save(grupo);

        var membro = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.user_id());

        if (membro == null) {
            throw new ValidacaoException("Usuário ou Grupo inválidos");
        }
        try {
            var lista = new Lista(membro, grupo, TipoUsuario.ADMIN);
            listaRepository.save(lista);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException ex) {
            throw new ValidacaoException("Não foi possivel adicionar Intergrante na lista pois ele já está na lista");
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<DadosDetalhaGrupo>> listarPorId(@PathVariable Long id) {
        List<DadosDetalhaGrupo> listaDeGrupos = listaRepository.findAllByUsuarioId(id)
                .stream().map(DadosDetalhaGrupo::new).toList();

        return ResponseEntity.ok(listaDeGrupos);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharPorId(@PathVariable Long id) {
        var grupo = grupoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhaGrupo(grupo));
    }

    @PostMapping("/del")
    @Transactional
    public ResponseEntity deletar(@RequestBody @Valid DadosInfoGrupoUser dados) {

        Lista user = listaRepository.getReferenceByUsuarioIdAndGrupoId(dados.user_id(), dados.grupo_id());

        if(user.getCargo() == TipoUsuario.ADMIN) {
            mensagemRepository.deleteAllByGrupoId(dados.grupo_id());
            listaRepository.deleteAllByGrupoId(dados.grupo_id());

            grupoRepository.deleteById(dados.grupo_id());
        } else {
            throw new ValidacaoException("Você não tem permissão de excluir usuários");
        }

        return ResponseEntity.noContent().build();
    }
}
