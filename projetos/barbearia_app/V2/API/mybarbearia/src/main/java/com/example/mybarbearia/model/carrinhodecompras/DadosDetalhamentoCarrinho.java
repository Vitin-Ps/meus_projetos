package com.example.mybarbearia.model.carrinhodecompras;

import java.math.BigDecimal;

public record DadosDetalhamentoCarrinho(
        Long idCliente,
        Long idProduto,
        Long idServico,
        BigDecimal preco,
        String duracao
) {
    public DadosDetalhamentoCarrinho(CarrinhoDeCompras carrinho) {
        this(
                carrinho.getCliente().getId(),
                carrinho.getProduto() != null ? carrinho.getProduto().getId() : null,
                carrinho.getServico() != null ? carrinho.getServico().getId() : null,
                carrinho.getPreco(),
                carrinho.getServico() != null ? carrinho.getServico().getDuracao() : null
        );
    }
}
