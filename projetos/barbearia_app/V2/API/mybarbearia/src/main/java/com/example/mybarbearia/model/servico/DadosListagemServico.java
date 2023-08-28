package com.example.mybarbearia.model.servico;

import java.math.BigDecimal;

public record DadosListagemServico(
        Long id,
        String nome,
        String Descricao,
        BigDecimal preco,
        String duracao
) {
    public DadosListagemServico(Servico servico) {
        this(servico.getId(), servico.getNome(), servico.getDescricao(), servico.getPreco(), servico.getDuracao());
    }
}
