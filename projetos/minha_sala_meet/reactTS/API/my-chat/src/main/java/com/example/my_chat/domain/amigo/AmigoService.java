package com.example.my_chat.domain.amigo;

import com.example.my_chat.domain.solicitacao.DadosRegistroSolicitacao;
import com.example.my_chat.domain.solicitacao.Solicitacao;
import com.example.my_chat.domain.usuario.Usuario;
import com.example.my_chat.infra.exception.ValidacaoException;
import com.example.my_chat.repository.SolicitacaoRepository;
import com.example.my_chat.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AmigoService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private AmigoRespository amigoRespository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void enviarSolicitacaoAmizade(DadosRegistroSolicitacao dados) {
        if (Objects.equals(dados.user_remetente_id(), dados.user_destinatario_id())) {
            throw new ValidacaoException("Erro. Você não pode pedir solicitação para si mesmo.");
        }

        Amigo amigoExistente = amigoRespository.getReferenceByUserIdAndAmigoId(dados.user_destinatario_id(), dados.user_remetente_id());

        if(amigoExistente != null) {
            throw new ValidacaoException("Você já adicionou este usuário.");
        }

        amigoExistente = amigoRespository.getReferenceByUserIdAndAmigoId(dados.user_remetente_id(), dados.user_destinatario_id());

        if(amigoExistente != null) {
            throw new ValidacaoException("Você já adicionou este usuário.");
        }

        var solicitacaoExistente = solicitacaoRepository.getReferenceByUserRemetenteIdAndUserDestinatarioId(dados.user_remetente_id(), dados.user_destinatario_id());

        if(solicitacaoExistente != null) {
            throw new ValidacaoException("Você já enviou uma solicitação para este usuário.");
        }

        solicitacaoExistente = solicitacaoRepository.getReferenceByUserRemetenteIdAndUserDestinatarioId(dados.user_destinatario_id(), dados.user_remetente_id());

        if(solicitacaoExistente != null) {
            throw new ValidacaoException("Você já tem uma solicitação do usuário selecionado.");
        }

        var userRemetente = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.user_remetente_id());
        var userDestinatario = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.user_destinatario_id());

        var solicitacao = new Solicitacao(userRemetente, userDestinatario);

        solicitacaoRepository.save(solicitacao);
    }

    public void addAmigo(DadosInfoAmigo dados) {
        if (Objects.equals(dados.user_id(), dados.amigo_id())) {
            throw new ValidacaoException("Erro. Você não pode adicionar a si mesmo.");
        }

        Amigo amigoExistente = amigoRespository.getReferenceByUserIdAndAmigoId(dados.user_id(), dados.amigo_id());

        if(amigoExistente != null) {
            throw new ValidacaoException("Você já adicionou este usuário.");
        }

        amigoExistente = amigoRespository.getReferenceByUserIdAndAmigoId(dados.amigo_id(), dados.user_id());

        if(amigoExistente != null) {
            throw new ValidacaoException("Você já adicionou este usuário.");
        }

        Usuario userOne = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.user_id());
        Usuario userTwo = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.amigo_id());

        Amigo novoAmigo = new Amigo(userOne, userTwo);
        amigoRespository.save(novoAmigo);

        novoAmigo = new Amigo(userTwo, userOne);
        amigoRespository.save(novoAmigo);

        var solicitacaoExistente = solicitacaoRepository.getReferenceByUserRemetenteIdAndUserDestinatarioId(dados.user_id(), dados.amigo_id());
        if(solicitacaoExistente != null) {
            solicitacaoRepository.delete((solicitacaoExistente));
        }

        solicitacaoExistente = solicitacaoRepository.getReferenceByUserRemetenteIdAndUserDestinatarioId(dados.amigo_id(), dados.user_id());
        if(solicitacaoExistente != null) {
            solicitacaoRepository.delete((solicitacaoExistente));
        }
    }

    public void rejeitarSolicitacaoAmizade(Long id) {
        Solicitacao solicitacao = solicitacaoRepository.getReferenceById(id);
        solicitacaoRepository.delete(solicitacao);
    }

    public void deletarAmigo(DadosInfoAmigo dados) {
        Amigo amigo = amigoRespository.getReferenceByUserIdAndAmigoId(dados.user_id(), dados.amigo_id());
        if(amigo != null) {
            amigoRespository.delete((amigo));
        }

        amigo = amigoRespository.getReferenceByUserIdAndAmigoId(dados.amigo_id(), dados.user_id());
        if(amigo != null) {
            amigoRespository.delete((amigo));
        }
    }
}
