package com.example.mybarbearia.model.carrinhodecompras;

import java.math.BigDecimal;

public record DadosListagemCarrinho(
        Long idProduto,
        Long idServico,
        BigDecimal preco
) {
    public DadosListagemCarrinho(CarrinhoDeCompras carrinho) {
        this(
                carrinho.getProduto() != null ? carrinho.getProduto().getId() : null,
                carrinho.getServico() != null ? carrinho.getServico().getId() : null,
                carrinho.getPreco()
        );
    }
}
