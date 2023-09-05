package com.example.mybarbearia.model.atendimento;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAtualizaAtendimento(
        @NotNull(message = "Id do Atendimento {dados.obrigatorio}")
        Long idAtendimento,
        Long idFuncionario,
        @Future
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,
        StatusAtendimento status
) {

}
