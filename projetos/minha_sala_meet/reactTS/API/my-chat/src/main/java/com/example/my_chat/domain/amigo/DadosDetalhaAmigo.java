package com.example.my_chat.domain.amigo;

import com.example.my_chat.domain.conversa.Conversa;
import com.example.my_chat.domain.usuario.DadosDetalhamentoUser;

public record DadosDetalhaAmigo(
        Long id,
        DadosDetalhamentoUser usuario,
        DadosDetalhamentoUser amigo
) {

    public DadosDetalhaAmigo(Amigo amigo) {
        this(amigo.getId(), new DadosDetalhamentoUser(amigo.getUsuario()), new DadosDetalhamentoUser(amigo.getAmigo()));
    }
}
