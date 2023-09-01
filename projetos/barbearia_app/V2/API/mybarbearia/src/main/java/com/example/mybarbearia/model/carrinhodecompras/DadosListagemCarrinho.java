package com.example.mybarbearia.model.carrinhodecompras;

import com.example.mybarbearia.model.produto.DadosListagemProduto;
import com.example.mybarbearia.model.servico.DadosListagemServico;

import java.math.BigDecimal;

public record DadosListagemCarrinho(
        DadosListagemProduto idProduto,
        DadosListagemServico idServico,
        BigDecimal preco
) {
    public DadosListagemCarrinho(CarrinhoDeCompras carrinho) {
        this(
                carrinho.getProduto() != null ? new DadosListagemProduto(carrinho.getProduto().getId(), carrinho.getProduto().getNome(), carrinho.getProduto().getDescricao(), carrinho.getProduto().getPreco(), carrinho.getProduto().getMarca(), carrinho.getProduto().getTipo(), carrinho.getProduto().getComissao()) : null,
                carrinho.getServico() != null ? new DadosListagemServico(carrinho.getServico().getId(), carrinho.getServico().getNome(), carrinho.getServico().getDescricao(), carrinho.getServico().getPreco(), carrinho.getServico().getDuracao()) : null,
                carrinho.getPreco()
        );
    }
}
