package com.example.my_chat.domain.grupo;

import com.example.my_chat.domain.conversa.Conversa;
import com.example.my_chat.domain.conversa.TipoConversa;
import com.example.my_chat.domain.membro.Membro;
import com.example.my_chat.domain.usuario.TipoUsuario;
import com.example.my_chat.infra.exception.ValidacaoException;
import com.example.my_chat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {
    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private MembroRepository membroRepository;

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConversaRepository conversaRepository;

    public DadosDetalhaGrupo cadastrarGrupo(DadosRegistroGrupo dados) {
        Conversa conversa = new Conversa(dados.uuid(), TipoConversa.GRUPO);
        conversa = conversaRepository.save(conversa);

        Grupo grupo = new Grupo(dados.nome(), conversa);
        grupo = grupoRepository.save(grupo);

        var membro = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.user_id());

        if (membro == null) {
            throw new ValidacaoException("Usuário ou Grupo inválidos");
        }
        try {
            var novoMembro = new Membro(membro, grupo, TipoUsuario.ADMIN);
            membroRepository.save(novoMembro);
        } catch (DataIntegrityViolationException ex) {
            throw new ValidacaoException("Não foi possivel adicionar Intergrante na lista pois ele já está na lista");
        }

        return new DadosDetalhaGrupo(grupo);
    }

    public List<DadosDetalhaGrupo> listarPorId(Long id) {
        return membroRepository.findAllByUsuarioId(id)
                .stream().map(DadosDetalhaGrupo::new).toList();
    }

    public Grupo detalharPorId(Long id) {
        return grupoRepository.getReferenceById(id);
    }

    public void deletarGrupo(DadosInfoGrupoUser dados) {
        Membro user = membroRepository.getReferenceByUsuarioIdAndGrupoId(dados.user_id(), dados.grupo_id());
        Grupo grupo = grupoRepository.getReferenceById(dados.grupo_id());

        if(user.getCargo() == TipoUsuario.ADMIN) {
            mensagemRepository.deleteAllByConversaId(grupo.getConversa().getId());
            membroRepository.deleteAllByGrupoId(dados.grupo_id());
            grupoRepository.delete(grupo);
            conversaRepository.delete(grupo.getConversa());
        } else {
            throw new ValidacaoException("Você não tem permissão de excluir usuários");
        }
    }
}
