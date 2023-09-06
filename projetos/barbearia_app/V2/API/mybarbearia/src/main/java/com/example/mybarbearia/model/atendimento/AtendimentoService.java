package com.example.mybarbearia.model.atendimento;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.model.atendimento.validadores.agendamento.ValidadorAgendar;
import com.example.mybarbearia.services.StringEmMinutos;
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
    private AtendimentoRepository atendimentoRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ReciboService reciboService;
    @Autowired
    private List<ValidadorAgendar> validadorAgendar;
    public DadosListagemAgendamento agendar(DadosAgendamentoAtendimento dados) {

        if(dados.idFuncionario() != null && !funcionarioRepository.existsById(dados.idFuncionario())) {
            throw new ValidacaoExeption("Funcionário não cadastrado");
        }
        if(dados.idCliente() != null && !clienteRepository.existsByIdAndAtivoTrue(dados.idCliente())) {
            throw new ValidacaoExeption("Cliente não existe");
        }

        var funcionario = escolherBarbeiro(dados); // metodo abaixo
        var dadosModificados = new DadosAgendamentoAtendimento(dados.idCliente(), funcionario.getId(), dados.data()); // cria um novo dados com o id funcionario, caso nenhum seja especificado
        validadorAgendar.forEach(vA -> vA.validar(dadosModificados)); // checa as regras e validadções
        var cliente = clienteRepository.getReferenceByIdAndAtivoTrue(dados.idCliente()); // procura um cliente pelo id com o ativo true

        var agendamentos = atendimentoRepository.findAllByFuncionarioIdAndStatus(funcionario.getId(), StatusAtendimento.PENDENTE);

        var atendimento = new Atendimento(null, cliente, funcionario, dados.data(), null, null, StatusAtendimento.PENDENTE); // cria um objeto atendimento
        atendimentoRepository.save(atendimento); // salva no db
        var listaRecibos = this.salvarDadosDoCarrinhoNoRecibo(atendimento); // metodo abaixo

        this.horarioDispovelComDuracao(dados, atendimento, agendamentos);

        return new DadosListagemAgendamento(atendimento.getId(), new DadosListagemCliente(atendimento.getCliente()), new DadosListagemFuncionario(atendimento.getFuncionario()), atendimento.getData(), listaRecibos);

    }
    public DadosListagemAgendamento atualizarDados(DadosAtualizaAtendimento dados) {
        var atendimento = atendimentoRepository.getReferenceById(dados.idAtendimento()); // pega o atendimento baseado no id
        var funcionario = dados.idFuncionario() != null ? funcionarioRepository.getReferenceByIdAndAtivoTrue(dados.idFuncionario()) : null; // se funcionario nao for null
        atendimento.atualizaDados(funcionario, dados.data(), dados.status());

        var listaRecibos = this.salvarDadosDoCarrinhoNoRecibo(atendimento); // metodo abaixo

        return new DadosListagemAgendamento(atendimento.getId(), new DadosListagemCliente(atendimento.getCliente()),new DadosListagemFuncionario(atendimento.getFuncionario()),atendimento.getData(),listaRecibos);
    }

    private Funcionario escolherBarbeiro(DadosAgendamentoAtendimento dados) { // metodo para escolher barbeiro caso o id nao for selecionado
        if(dados.idFuncionario() != null) {
            var funcionario = funcionarioRepository.getReferenceByIdAndAtivoTrue(dados.idFuncionario());
            if(funcionario.getCargo() == Cargo.BARBEIRO) return funcionario; // se o cargo do funcionro for barbeiro
            else throw new ValidacaoExeption("Funcionário não é de um Barbeiro"); // se não
        }
        if(funcionarioRepository.escolherFuncionarioComAgendaLivreNoHorario(dados.data()) != null) { // checa se o resultado da consulta nao e null, se for e porque nenhum funconario esta dipovinel no horario
            return funcionarioRepository.escolherFuncionarioComAgendaLivreNoHorario(dados.data());
        } else {
            throw new ValidacaoExeption("Não há funcionario disponivel esse horário");
        }
      }

    private List<DadosListagemRecibo> salvarDadosDoCarrinhoNoRecibo (Atendimento atendimento) { //esse método salva os dados do carrinho no recibo e limpa todos os intens que estiverem relacionado ao id do cliente
        List<String> listaDuracao = new ArrayList<>();
        BigDecimal precoTotal = BigDecimal.ZERO;
        List<DadosListagemRecibo> listaRecibos = new ArrayList<>();
        var itensDoRecibo = reciboService.gerarRecibo(atendimento); // metodo do reciboService
        for (DadosDetalhamentoRecibo r : itensDoRecibo) {
            precoTotal = precoTotal.add(r.recibo().getPreco()); // add o preco dos itens do carrinho. usado para ser salvo no atendimento
            listaDuracao.add(r.duracao()); // salva as durações dos serviços, se for um produto, salvara null, mas o metodo que soma já lida com esse caso
            listaRecibos.add(new DadosListagemRecibo(r.recibo())); // salva o recibo numa lista
        }
        atendimento.addPrecoEDuracao(StringEmMinutos.converterDateParaString(StringEmMinutos.duracaoTotal(listaDuracao)), precoTotal); // insere o preco e duração no atendimento
        return listaRecibos; // retorna a lista com informações do recibo
    }

    private void horarioDispovelComDuracao (DadosAgendamentoAtendimento dados, Atendimento atendimento, List<Atendimento> agendamentos) {

        agendamentos.forEach(ag -> {
            System.out.println(ag.getId());
            var minutos = StringEmMinutos.converterParaMinutosEmRacional(atendimento.getDuracao());
            var horarioIni = ag.getData().minusMinutes(minutos);
            var horarioFim = ag.getData();
            var novaData = dados.data();
            if (novaData.isAfter(horarioIni) && novaData.isBefore(horarioFim)) throw new ValidacaoExeption("A duração dos seus serviços está conflitando com um atendimento posterior!!! \nDiminua o tempo do seu atendimento e tente novamente.");

        });
    }

}
