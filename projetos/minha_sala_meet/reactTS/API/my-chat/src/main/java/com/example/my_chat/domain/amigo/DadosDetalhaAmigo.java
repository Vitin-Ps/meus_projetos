package com.example.my_chat.domain.amigo;

import com.example.my_chat.domain.usuario.DadosDetalhamentoUser;

public record DadosDetalhaAmigo(
        Long id,
        DadosDetalhamentoUser user,
        DadosDetalhamentoUser amigo
) {

    public DadosDetalhaAmigo(Amigo amigo) {
        this(amigo.getId(), new DadosDetalhamentoUser(amigo.getUser()), new DadosDetalhamentoUser(amigo.getAmigo()));
    }
}
