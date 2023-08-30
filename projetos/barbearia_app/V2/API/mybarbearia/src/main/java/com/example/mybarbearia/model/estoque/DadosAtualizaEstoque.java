package com.example.mybarbearia.model.estoque;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record DadosAtualizaEstoque(
        Long idProduto,
        Integer quantidade,
        AlterarQuantidade alterarQuantidade
) {
}
