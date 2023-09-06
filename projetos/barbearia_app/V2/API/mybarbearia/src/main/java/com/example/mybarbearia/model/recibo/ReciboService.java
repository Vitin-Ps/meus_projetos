package com.example.mybarbearia.model.recibo;

import com.example.mybarbearia.model.atendimento.Atendimento;
import com.example.mybarbearia.model.carrinhodecompras.CarrinhoService;
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
    CarrinhoService carrinhoService;

    public List<DadosDetalhamentoRecibo> gerarRecibo(Atendimento atendimento) { // salva os dados do carrinho no recibo

        var recibos = carrinhoService.finalizarPedido(atendimento.getCliente().getId()); // metodo do carrinhoservice, devolve uma lista com as informações do item para salvar os dados no carrinho
        List<DadosDetalhamentoRecibo> itensDoRecibo = new ArrayList<>();

        recibos.forEach(r -> {
            if(r.produto() != null) {
                var recibo = new Recibo(null, atendimento, r.produto(), null, r.quantidade(), r.preco()); // cria o objeto
                reciboRepository.save(recibo); // salva no banco
                itensDoRecibo.add(new DadosDetalhamentoRecibo(recibo, null)); // adicona o obejto e a duracao em outra lista que vai ser o retorno do método
            }
            if(r.servico() != null) { // mesma coisa do produto
                var recibo = new Recibo(null, atendimento, null, r.servico(), r.quantidade(), r.preco());
                reciboRepository.save(recibo);
                itensDoRecibo.add(new DadosDetalhamentoRecibo(recibo, r.duracao()));
            }
        });


        return itensDoRecibo; // lista contendo os obejtos recibo e as durações em um array de string que vai ser usado para calcular a diração atraves so método StringEmMinutos
    }
}
