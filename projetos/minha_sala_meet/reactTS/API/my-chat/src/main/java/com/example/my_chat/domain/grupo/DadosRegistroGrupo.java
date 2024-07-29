package com.example.my_chat.domain.grupo;

import jakarta.validation.constraints.NotBlank;

public record DadosRegistroGrupo(
        @NotBlank(message = "Nome é Obrigatório")
        String nome,
        @NotBlank(message = "UUID é Obrigatório")
        String uuid
) {
}
