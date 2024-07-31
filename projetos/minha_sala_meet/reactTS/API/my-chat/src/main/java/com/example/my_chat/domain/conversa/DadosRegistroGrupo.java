package com.example.my_chat.domain.conversa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosRegistroGrupo(
        @NotBlank(message = "Nome é Obrigatório")
        String nome,
        @NotBlank(message = "UUID é Obrigatório")
        String uuid,
        @NotNull(message = "Usuário é Obrigatório")
        Long user_id

) {
}
