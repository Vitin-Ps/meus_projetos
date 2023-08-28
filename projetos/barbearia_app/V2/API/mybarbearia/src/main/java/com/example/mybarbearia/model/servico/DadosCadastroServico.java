package com.example.mybarbearia.model.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosCadastroServico(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        String descricao,
        @NotNull(message = "Preço é obrigatório")
        BigDecimal preco,
        @NotBlank(message = "Duração é obrigatório")
        String duracao
) {
}
