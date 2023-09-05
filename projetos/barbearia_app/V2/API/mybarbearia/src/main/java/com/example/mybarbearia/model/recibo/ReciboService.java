package com.example.mybarbearia.model.recibo;

import com.example.mybarbearia.model.atendimento.Atendimento;
import com.example.mybarbearia.model.carrinhodecompras.FuncionalidadesDoCarrinho;
import com.example.mybarbearia.repository.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReciboService {
    @Autowired
    ReciboRepository reciboRepository;
    @Autowired
    FuncionalidadesDoCarrinho fCarrinho;

    public List<DadosDetalhamentoRecibo> gerarRecibo(Atendimento atendimento) {

        var recibos = fCarrinho.finalizarPedido(atendimento.getCliente().getId());
        List<DadosDetalhamentoRecibo> itensDoRecibo = new ArrayList<>();

        recibos.forEach(r -> {
            if(r.produto() != null) {
                var recibo = new Recibo(null, atendimento, r.produto(), null, r.quantidade(), r.preco());
                reciboRepository.save(recibo);
                itensDoRecibo.add(new DadosDetalhamentoRecibo(recibo, null));
            }
            if(r.servico() != null) {
                var recibo = new Recibo(null, atendimento, null, r.servico(), r.quantidade(), r.preco());
                reciboRepository.save(recibo);
                itensDoRecibo.add(new DadosDetalhamentoRecibo(recibo, r.duracao()));
            }
        });


        return itensDoRecibo;
    }
}
