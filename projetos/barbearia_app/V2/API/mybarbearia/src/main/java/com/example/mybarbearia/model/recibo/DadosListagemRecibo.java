package com.example.mybarbearia.model.recibo;

import com.example.mybarbearia.model.produto.Produto;
import com.example.mybarbearia.model.servico.Servico;

import java.math.BigDecimal;

public record DadosListagemRecibo(
        Produto produto,
        Servico servico,
        Integer quantidade,
        BigDecimal preco
) {
    public DadosListagemRecibo(Recibo recibo) {
        this(
                recibo.getProduto(),
                recibo.getServico(),
                recibo.getQuantidade(),
                recibo.getPreco()
        );
    }
}
