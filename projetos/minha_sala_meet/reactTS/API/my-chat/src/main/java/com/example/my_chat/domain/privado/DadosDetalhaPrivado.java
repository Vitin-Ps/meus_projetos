package com.example.my_chat.domain.privado;

import com.example.my_chat.domain.conversa.Conversa;
import com.example.my_chat.domain.usuario.DadosDetalhamentoUser;

public record DadosDetalhaPrivado(
        Long id,
        DadosDetalhamentoUser userOne,
        DadosDetalhamentoUser userTwo,
        Conversa conversa
) {

    public DadosDetalhaPrivado(Privado privado) {
        this(privado.getId(), new DadosDetalhamentoUser(privado.getUserOne()), new DadosDetalhamentoUser(privado.getUserTwo()), privado.getConversa());
    }
}
