package com.example.my_chat.domain.conversa;

import jakarta.validation.constraints.NotNull;

public record DadosInfoConversaUser(
        @NotNull
        Long user_one_id,
        @NotNull
        Long conversa_id
) {
}
