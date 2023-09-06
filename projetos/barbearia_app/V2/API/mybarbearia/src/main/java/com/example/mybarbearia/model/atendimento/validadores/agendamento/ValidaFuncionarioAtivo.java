package com.example.mybarbearia.model.atendimento.validadores.agendamento;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.model.atendimento.DadosAgendamentoAtendimento;
import com.example.mybarbearia.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaFuncionarioAtivo implements ValidadorAgendar{
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Override
    public void validar(DadosAgendamentoAtendimento dados) {
        if(dados.idFuncionario() == null) {
            return;
        }
        var funcionarioEstaAtivo = funcionarioRepository.existsByIdAndAtivoTrue(dados.idFuncionario());
        if(!funcionarioEstaAtivo) throw new ValidacaoExeption("Funcionário não encontrado");
    }
}
