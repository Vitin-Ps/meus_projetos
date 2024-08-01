package com.example.my_chat.domain.amigo;

import jakarta.validation.constraints.NotNull;

public record DadosInfoAmigo(
        @NotNull
        Long user_id,
        @NotNull
        Long amigo_id
) {
}
