package com.example.mybarbearia.model.atendimento.validadores.agendamento;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.model.atendimento.DadosAgendamentoAtendimento;
import com.example.mybarbearia.model.atendimento.StatusAtendimento;
import com.example.mybarbearia.repository.AtendimentoRepository;
import com.example.mybarbearia.services.StringEmMinutos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioDisponivel implements ValidadorAgendar{
    @Autowired
    AtendimentoRepository atendimentoRepository;
    @Override
    public void validar(DadosAgendamentoAtendimento dados) {
        var agendamentos = atendimentoRepository.findAllByFuncionarioIdAndStatus(dados.idFuncionario(), StatusAtendimento.PENDENTE);

        agendamentos.forEach(ag -> {
            System.out.println(ag.getId());
            var minutos = StringEmMinutos.converterParaMinutosEmRacional(ag.getDuracao());
            var horarioIni = ag.getData().minusMinutes(1);
            var horarioFim = ag.getData().plusMinutes(minutos);
            var novaData = dados.data();
            if (novaData.isAfter(horarioIni) && novaData.isBefore(horarioFim)) throw new ValidacaoExeption("O barbeiro está ocupado nesse horario");
        });
    }
}
