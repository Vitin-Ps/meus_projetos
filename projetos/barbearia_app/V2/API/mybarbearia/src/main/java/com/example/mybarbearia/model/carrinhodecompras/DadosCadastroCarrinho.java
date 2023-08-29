package com.example.mybarbearia.model.carrinhodecompras;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosCadastroCarrinho(
        @NotNull(message = "Id do Cliente {dados.obrigatorio}")
        Long idCliente,
        @NotNull(message = "Id do Item {dados.obrigatorio}")
        Long idItem,
        @NotNull(message = "Tipo do Item {dados.obrigatorio}")
        TipoItem tipoItem,
        @NotNull(message = "Preço {dados.obrigatorio}")
        BigDecimal preco
) {
}
