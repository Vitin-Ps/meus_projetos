package com.example.mybarbearia.model.carrinhodecompras;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosCadastroCarrinho(
        @NotNull(message = "Id do Cliente {dados.obrigatorio}")
        Long idCliente,
        Long idProduto,
        Long idServico
) {
}
