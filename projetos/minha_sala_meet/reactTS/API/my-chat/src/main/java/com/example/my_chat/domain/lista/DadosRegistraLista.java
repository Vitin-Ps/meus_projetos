package com.example.my_chat.domain.lista;

import com.example.my_chat.domain.usuario.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosRegistraLista(
        @NotNull(message = "ID do usuário é Obrigatório")
        Long usuario_id,
        @NotBlank(message = "Grupo é Obrigatório")
        String grupo_id,
        @NotNull(message = "Cargo é Obrigatório")
        TipoUsuario cargo
) {
}
