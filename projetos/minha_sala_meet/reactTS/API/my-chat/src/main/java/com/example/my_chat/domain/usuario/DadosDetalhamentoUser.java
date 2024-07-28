package com.example.my_chat.domain.usuario;

public record DadosDetalhamentoUser(
        Long id,
        String nome,
        String imagem
) {

    public DadosDetalhamentoUser(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getImagem());
    }
}
