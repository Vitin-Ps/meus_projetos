package com.example.my_chat.domain.mensagem;

import com.example.my_chat.domain.conversa.DadosDetalhaConversa;

import com.example.my_chat.domain.usuario.DadosDetalhamentoUser;

import java.time.LocalDateTime;

public record DadosDetalhaMensagem(
        Long id,
        DadosDetalhamentoUser userRemetente,
        DadosDetalhaConversa conversa,
        String mensagem,
        LocalDateTime data
) {
    public DadosDetalhaMensagem(Mensagem mensagem) {
        this(mensagem.getId(), new DadosDetalhamentoUser(mensagem.getUserRemetente()), new DadosDetalhaConversa(mensagem.getConversa()), mensagem.getMensagem(), mensagem.getData());
    }
}
