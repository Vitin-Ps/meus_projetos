package com.example.my_chat.domain.privado;

import com.example.my_chat.domain.conversa.Conversa;
import com.example.my_chat.domain.conversa.DadosDetalhaConversa;
import com.example.my_chat.domain.usuario.DadosDetalhamentoUser;

public record DadosDetalhaPrivado(
        Long id,
        DadosDetalhamentoUser userOne,
        DadosDetalhamentoUser userTwo,
        DadosDetalhaConversa conversa
) {

    public DadosDetalhaPrivado(Privado privado) {
        this(privado.getId(), new DadosDetalhamentoUser(privado.getUserOne()), new DadosDetalhamentoUser(privado.getUserTwo()), new DadosDetalhaConversa(privado.getConversa()));
    }
}
