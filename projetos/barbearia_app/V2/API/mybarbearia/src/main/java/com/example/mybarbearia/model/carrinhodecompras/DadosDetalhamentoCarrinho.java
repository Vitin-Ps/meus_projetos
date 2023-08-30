package com.example.mybarbearia.model.carrinhodecompras;

import java.math.BigDecimal;

public record DadosDetalhamentoCarrinho(
        Long id,
        Long idCliente,
        Long idProduto,
        Long idServico,
        BigDecimal preco
) {
    public DadosDetalhamentoCarrinho(CarrinhoDeCompras carrinho) {
        this(carrinho.getId(),
                carrinho.getCliente().getId(),
                carrinho.getProduto() != null ? carrinho.getProduto().getId() : null,
                carrinho.getServico() != null ? carrinho.getServico().getId() : null,
                carrinho.getPreco());
    }
}
