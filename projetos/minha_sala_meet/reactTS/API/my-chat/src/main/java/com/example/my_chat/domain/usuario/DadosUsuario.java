package com.example.my_chat.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosUsuario(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Login é obrigatório")
        String login,
        @NotBlank(message = "Senha é obrigatória")
        String senha

) {
}
