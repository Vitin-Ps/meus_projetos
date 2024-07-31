package com.example.my_chat.domain.membro;

import com.example.my_chat.domain.usuario.TipoUsuario;
import com.example.my_chat.infra.exception.ValidacaoException;
import com.example.my_chat.repository.GrupoRepository;
import com.example.my_chat.repository.MembroRepository;
import com.example.my_chat.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class MembroService {
    @Autowired
    private MembroRepository membroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GrupoRepository grupoRepository;


    public void addIntegranteMembro(DadosRegistraLista dados) {
        var user = membroRepository.getReferenceByUsuarioIdAndGrupoId(dados.user_id(), dados.grupo_id());
        var membro = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.membro_id());
        var grupo = grupoRepository.getReferenceById(dados.grupo_id());

        if (membro == null || user == null) {
            throw new ValidacaoException("Usuário ou Grupo inválidos");
        }

        if(user.getCargo() == TipoUsuario.ADMIN) {
            try {
                var novoMembro = new Membro(membro, grupo, TipoUsuario.USER);
                membroRepository.save(novoMembro);
            } catch (DataIntegrityViolationException ex) {
                throw new ValidacaoException("Não foi possivel adicionar Intergrante na lista pois ele já está na lista");
            }
        } else {
            throw new ValidacaoException("Você não tem permissão de excluir usuários");
        }
    }

    public void delintegrante(DadosDeletaMembroLista dados) {
        Membro membro = membroRepository.getReferenceByUsuarioIdAndGrupoId(dados.membro_id(), dados.grupo_id());

        Membro user = membroRepository.getReferenceByUsuarioIdAndGrupoId(dados.user_id(), dados.grupo_id());

        if(user.getCargo() == TipoUsuario.ADMIN) {
            membroRepository.delete(membro);
        } else {
            throw new ValidacaoException("Você não tem permissão de excluir usuários");
        }
    }
}
