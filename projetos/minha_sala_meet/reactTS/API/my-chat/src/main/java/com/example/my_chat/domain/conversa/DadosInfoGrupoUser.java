package com.example.my_chat.domain.conversa;

import jakarta.validation.constraints.NotNull;

public record DadosInfoGrupoUser(
        @NotNull
        Long user_id,
        @NotNull
        Long grupo_id
) {
}
