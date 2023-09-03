package com.example.mybarbearia.model.atendimento;

import com.example.mybarbearia.model.cliente.DadosListagemCliente;
import com.example.mybarbearia.model.funcionario.DadosListagemFuncionario;
import com.example.mybarbearia.model.recibo.DadosDetalhamentoRecibo;
import com.example.mybarbearia.model.recibo.DadosListagemRecibo;

import java.time.LocalDateTime;
import java.util.List;

public record DadosListagemAgendamento(
        Long idAtendimento,
        DadosListagemCliente cliente,
        DadosListagemFuncionario funcionario,
        LocalDateTime data,
        List<DadosListagemRecibo> recibo
) {
}
