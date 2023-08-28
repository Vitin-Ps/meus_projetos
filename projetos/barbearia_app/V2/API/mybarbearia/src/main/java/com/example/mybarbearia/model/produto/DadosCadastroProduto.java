package com.example.mybarbearia.model.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosCadastroProduto(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        String descricao,
        @NotNull(message = "Preço do produto é obrigatório")
        BigDecimal preco,
        @NotBlank(message = "Marca é obrigatório")
        String marca,
        @NotNull(message = "Tipo do produto é obrigatório")
        TipoProduto tipo,
        @NotNull(message = "Comissao é obrigatório")
        BigDecimal comissao
) {
}
