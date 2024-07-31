package com.example.my_chat.domain.usuario;

import com.example.my_chat.domain.lista.Lista;

public record DadosDetalhamentoUser(
        Long id,
        String nome,
        String imagem
) {

    public DadosDetalhamentoUser(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getImagem());
    }

    public DadosDetalhamentoUser(Lista lista) {
        this(lista.getUsuario().getId(), lista.getUsuario().getNome(), lista.getUsuario().getImagem());
    }
}
