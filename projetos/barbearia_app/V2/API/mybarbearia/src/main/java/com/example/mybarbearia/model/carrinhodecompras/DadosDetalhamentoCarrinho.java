package com.example.mybarbearia.model.carrinhodecompras;

import java.math.BigDecimal;

public record DadosDetalhamentoCarrinho(
        Long id,
        Long idCliente,
        Long idProduto,
        Long idSercvico,
        BigDecimal preco
) {
}
