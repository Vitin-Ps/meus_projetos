package com.example.mybarbearia.model.atendimento.validadores.agendamento;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.model.atendimento.DadosAgendamentoAtendimento;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendar{

    @Override
    public void validar(DadosAgendamentoAtendimento dados) {
        var dataConsulta = dados.data();
        var agora= LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if(diferencaEmMinutos < 20) {
            throw new ValidacaoExeption("Atendimento tem que ser agendado com 20 minutos de antecedência");
        }
    }
}
