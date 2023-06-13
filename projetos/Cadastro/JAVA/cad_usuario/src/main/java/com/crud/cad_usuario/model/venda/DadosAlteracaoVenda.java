package com.crud.cad_usuario.model.venda;

import java.math.BigDecimal;

public record DadosAlteracaoVenda(Long idVenda, String funcionario, BigDecimal valorVenda, BigDecimal descontoVenda) {
}
