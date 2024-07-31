package com.example.my_chat.domain.solicitacao;

import jakarta.validation.constraints.NotNull;

public record DadosRegistroSolicitacao(
        @NotNull(message = "Usuário remetente é Obrigatório")
        String user_remetente_id,
        @NotNull(message = "Usuário destinatário é Obrigatório")
        Long user_destinatario_id

) {
}
