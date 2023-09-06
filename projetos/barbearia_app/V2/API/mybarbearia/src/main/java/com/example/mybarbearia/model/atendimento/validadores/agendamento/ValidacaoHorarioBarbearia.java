package com.example.mybarbearia.model.atendimento.validadores.agendamento;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.model.atendimento.DadosAgendamentoAtendimento;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacaoHorarioBarbearia implements ValidadorAgendar {
    @Override
    public void validar(DadosAgendamentoAtendimento dados) {
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesAberturaBarbearia = dataConsulta.getHour() == 8 && dataConsulta.getMinute() < 30;
        var depoisHorario = dataConsulta.getHour() > 20;

        if (domingo || antesAberturaBarbearia || depoisHorario) {
            throw new ValidacaoExeption("Barbearia não funciona nesse horário");
        }
    }
}
