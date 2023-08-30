package com.example.mybarbearia.model.carrinhodecompras;

import java.math.BigDecimal;

public
record DadosSomaItens(
        Long idProduto,
        Integer quantidadeProduto,
        Long idCliente,
        Integer quantidadeCliente,
        BigDecimal valorTotal

        ) {
}
