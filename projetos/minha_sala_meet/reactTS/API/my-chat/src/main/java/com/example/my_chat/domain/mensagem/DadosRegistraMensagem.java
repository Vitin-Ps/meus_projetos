package com.example.my_chat.domain.mensagem;

import com.example.my_chat.domain.grupo.Grupo;
import com.example.my_chat.domain.usuario.TipoUsuario;
import com.example.my_chat.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public record DadosRegistraMensagem(
        @NotNull(message = "Usuario é Obrigatório")
        Long usuario_id,
        @NotNull(message = "Grupo é Obrigatório")
        Long grupo_id,
        @NotNull(message = "Mensagem é Obrigatório")
        String mensagem,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data
) {
}
