package com.example.mybarbearia.model.estoque;

import com.example.mybarbearia.model.produto.DadosListagemProduto;
import com.example.mybarbearia.model.produto.Produto;

import java.math.BigDecimal;

public record DadosListagemEstoque(
        Long id,
        Integer quantidade,
        DadosListagemProduto produto
) {
    public DadosListagemEstoque(Estoque estoque, Produto produto) {
        this(estoque.getId(), estoque.getQuantidade(), new DadosListagemProduto(produto));
    }
}
