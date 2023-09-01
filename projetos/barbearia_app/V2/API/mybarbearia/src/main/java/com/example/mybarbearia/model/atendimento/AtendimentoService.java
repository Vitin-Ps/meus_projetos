package com.example.mybarbearia.model.atendimento;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.model.funcionario.Cargo;
import com.example.mybarbearia.model.funcionario.Funcionario;
import com.example.mybarbearia.model.recibo.ReciboService;
import com.example.mybarbearia.repository.AtendimentoRepository;
import com.example.mybarbearia.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtendimentoService {
    @Autowired
    AtendimentoRepository atendimentoRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    ReciboService reciboService;

    public DadosListagemAgendamento agendar(DadosAgendamentoAtendimento dados) {

        reciboService.gerarRecibo(dados.idCliente());

//        var funcionario = escolherBarbeiro(dados);
//
////        var atendimento = new Atendimento(null, null, funcionario, dados.data(), null, null);
////        atendimentoRepository.save(atendimento);
////        var recibo = reciboService.gerarRecibo(atendimento);
        return null;

    }

    private Funcionario escolherBarbeiro(DadosAgendamentoAtendimento dados) {
        if(dados.idFuncionario() != null) {
            var funcionario = funcionarioRepository.getReferenceByIdAndAtivoTrue(dados.idFuncionario());
            if(funcionario.getCargo() == Cargo.BARBEIRO) return funcionario;
            else throw new ValidacaoExeption("Id informado não é de um Barbeiro");
        }

        return funcionarioRepository.escolherFuncionarioComAgendaLivreNoHorario(dados.data());
    }
}
