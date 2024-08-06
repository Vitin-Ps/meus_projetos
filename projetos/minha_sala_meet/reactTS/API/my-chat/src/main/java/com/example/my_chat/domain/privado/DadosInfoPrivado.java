package com.example.my_chat.domain.privado;

import jakarta.validation.constraints.NotNull;

public record DadosInfoPrivado(
        @NotNull(message = "Usuário One é Obrigatório")
        Long user_one_id,
        @NotNull(message = "Usuário Two é Obrigatório")
        Long user_two_id) {

}
