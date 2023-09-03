package com.example.mybarbearia.model.atendimento;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.infra.services.StringEmMinutos;
import com.example.mybarbearia.model.cliente.DadosListagemCliente;
import com.example.mybarbearia.model.funcionario.Cargo;
import com.example.mybarbearia.model.funcionario.DadosListagemFuncionario;
import com.example.mybarbearia.model.funcionario.Funcionario;
import com.example.mybarbearia.model.recibo.DadosDetalhamentoRecibo;
import com.example.mybarbearia.model.recibo.DadosListagemRecibo;
import com.example.mybarbearia.model.recibo.ReciboService;
import com.example.mybarbearia.repository.AtendimentoRepository;
import com.example.mybarbearia.repository.ClienteRepository;
import com.example.mybarbearia.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AtendimentoService {
    @Autowired
    AtendimentoRepository atendimentoRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ReciboService reciboService;

    public DadosListagemAgendamento agendar(DadosAgendamentoAtendimento dados) {

        if(dados.idFuncionario() != null && !funcionarioRepository.existsById(dados.idFuncionario())) {
            throw new ValidacaoExeption("Funcionário não cadastrado");
        }
        if(dados.idCliente() != null && !clienteRepository.existsByIdAndAtivoTrue(dados.idCliente())) {
            throw new ValidacaoExeption("Cliente não existe");
        }

        var funcionario = escolherBarbeiro(dados);
        var cliente = clienteRepository.getReferenceByIdAndAtivoTrue(dados.idCliente());


        var atendimento = new Atendimento(null, cliente, funcionario, dados.data(), null, null);
        atendimentoRepository.save(atendimento);

        List<String> listaDuracao = new ArrayList<>();
        BigDecimal precoTotal = BigDecimal.ZERO;
        List<DadosListagemRecibo> listaRecibos = new ArrayList<>();
        var itensDoRecibo = reciboService.gerarRecibo(atendimento);
        for (DadosDetalhamentoRecibo r : itensDoRecibo) {
            precoTotal = precoTotal.add(r.recibo().getPreco());
            listaDuracao.add(r.duracao());
            listaRecibos.add(new DadosListagemRecibo(r.recibo()));
        }
        var date = StringEmMinutos.duracaoTotal(listaDuracao);

        atendimento.addPrecoEDuracao(StringEmMinutos.converterDateParaString(date), precoTotal);

        return new DadosListagemAgendamento(atendimento.getId(), new DadosListagemCliente(atendimento.getCliente()), new DadosListagemFuncionario(atendimento.getFuncionario()), atendimento.getData(), listaRecibos);

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
