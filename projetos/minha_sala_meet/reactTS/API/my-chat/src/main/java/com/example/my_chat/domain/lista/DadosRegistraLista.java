package com.example.my_chat.domain.lista;

import com.example.my_chat.domain.usuario.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosRegistraLista(
        @NotNull(message = "ID do usuário é Obrigatório")
        Long user_id,
        @NotNull(message = "Grupo é Obrigatório")
        Long grupo_id,
        @NotNull(message = "ID do usuário é Obrigatório")
        Long membro_id
) {
}
