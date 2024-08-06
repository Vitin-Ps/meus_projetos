package com.example.my_chat.domain.membro;

import jakarta.validation.constraints.NotNull;

public record DadosRegistraMembro(
        @NotNull(message = "ID do usuário é Obrigatório")
        Long user_id,
        @NotNull(message = "Grupo é Obrigatório")
        Long grupo_id,
        @NotNull(message = "ID do usuário é Obrigatório")
        Long membro_id
) {
}
