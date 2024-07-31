package com.example.my_chat.domain.usuario;

import com.example.my_chat.domain.membro.Membro;

public record DadosDetalhamentoUser(
        Long id,
        String nome,
        String imagem
) {

    public DadosDetalhamentoUser(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getImagem());
    }

    public DadosDetalhamentoUser(Membro lista) {
        this(lista.getUsuario().getId(), lista.getUsuario().getNome(), lista.getUsuario().getImagem());
    }
}
