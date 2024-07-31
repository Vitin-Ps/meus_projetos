package com.example.my_chat.domain.solicitacao;

import com.example.my_chat.domain.conversa.Conversa;
import com.example.my_chat.domain.usuario.DadosDetalhamentoUser;

public record DadosDetalhaSolicitacao(
        Long id,
        DadosDetalhamentoUser userRemetente,
        DadosDetalhamentoUser userDestinatario
) {

    public DadosDetalhaSolicitacao(Solicitacao solicitacao) {
        this(solicitacao.getId(), new DadosDetalhamentoUser(solicitacao.getUserRemetente()), new DadosDetalhamentoUser(solicitacao.getUserDestinatario()));
    }
}
