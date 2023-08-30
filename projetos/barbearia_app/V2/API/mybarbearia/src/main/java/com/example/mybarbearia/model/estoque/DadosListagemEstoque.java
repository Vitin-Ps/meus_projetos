package com.example.mybarbearia.model.estoque;

import com.example.mybarbearia.model.produto.DadosListagemProduto;
import com.example.mybarbearia.model.produto.Produto;

import java.math.BigDecimal;

public record DadosListagemEstoque(
        Long idProduto,
        Integer quantidade,
        DadosListagemProduto produto
) {
    public DadosListagemEstoque(Estoque estoque) {
        this(estoque.getProduto().getId(), estoque.getQuantidade(), new DadosListagemProduto(estoque.getProduto()));
    }
}
