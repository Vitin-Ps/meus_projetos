package com.example.mybarbearia.model.servico;

import java.math.BigDecimal;

public record DadosAtualizaServico(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        String duracao
) {
}
