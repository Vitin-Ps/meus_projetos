package com.example.my_chat.domain.privado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosRegistroGrupo(
        @NotNull(message = "Usuário One é Obrigatório")
        String user_one_id,
        @NotNull(message = "Usuário Two é Obrigatório")
        Long user_two_id,
        @NotNull(message = "Conversa é Obrigatório")
        Long conversa_id

) {
}
