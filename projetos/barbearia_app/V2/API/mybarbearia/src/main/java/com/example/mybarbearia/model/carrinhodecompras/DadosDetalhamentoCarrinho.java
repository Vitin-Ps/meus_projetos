package com.example.mybarbearia.model.carrinhodecompras;

import java.math.BigDecimal;

public record DadosListagemCarrinho(
        Long id,
        Long idCliente,
        Long idProduto,
        Long idSercvico,
        BigDecimal preco
) {
}
