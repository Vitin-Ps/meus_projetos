package com.example.my_chat.domain.lista;

import com.example.my_chat.domain.usuario.TipoUsuario;
import jakarta.validation.constraints.NotNull;

public record DadosAlteraSituacaoLista(
        @NotNull(message = "ID é Obrigatório")
        Long id,
        @NotNull(message = "Cargo é Obrigatório")
        TipoUsuario cargo
) {
}
