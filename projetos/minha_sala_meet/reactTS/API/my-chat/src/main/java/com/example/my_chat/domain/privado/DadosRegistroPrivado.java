package com.example.my_chat.domain.privado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosRegistroPrivado(
        @NotNull(message = "Usuário One é Obrigatório")
        Long user_one_id,
        @NotNull(message = "Usuário Two é Obrigatório")
        Long user_two_id,
        @NotBlank(message = "UUID Two é Obrigatório")
        String uuid
) {
}
