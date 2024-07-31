package com.example.my_chat.domain.lista;

import jakarta.validation.constraints.NotNull;

public record DadosDeletaMembroLista(
        @NotNull
        Long membro_id,
        @NotNull
        Long user_id,
        @NotNull
        Long grupo_id
) {
}
