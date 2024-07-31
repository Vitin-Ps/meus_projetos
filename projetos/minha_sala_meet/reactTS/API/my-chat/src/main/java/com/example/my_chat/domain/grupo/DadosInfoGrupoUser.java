package com.example.my_chat.domain.grupo;

import jakarta.validation.constraints.NotNull;

public record DadosInfoGrupoUser(
        @NotNull
        Long user_id,
        @NotNull
        Long grupo_id
) {
}
