package com.example.mybarbearia.model.atendimento;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoAtendimento(
    @NotNull(message = "Id do cliente {dados.obrigatorio}")
    Long idCliente,
    Long idFuncionario,
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime data
) {
}
