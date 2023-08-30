package com.example.mybarbearia.model.recibo;

import java.math.BigDecimal;

public
record DadosCriacaoRecibo(
        Long idProduto,
        Integer quantidadeProduto,
        Long idCliente,
        Integer quantidadeCliente,
        BigDecimal valorTotal

        ) {
}
