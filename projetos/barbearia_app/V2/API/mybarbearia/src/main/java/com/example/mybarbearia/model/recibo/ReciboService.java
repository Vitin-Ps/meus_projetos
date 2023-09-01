package com.example.mybarbearia.model.recibo;

import com.example.mybarbearia.model.atendimento.Atendimento;
import com.example.mybarbearia.model.carrinhodecompras.FuncionalidadesDoCarrinho;
import com.example.mybarbearia.repository.CarrinhoDeComprasRepository;
import com.example.mybarbearia.repository.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReciboService {
    @Autowired
    ReciboRepository reciboRepository;
    @Autowired
    FuncionalidadesDoCarrinho fCarrinho;

    public List<Recibo> gerarRecibo(Long atendimento) {

        fCarrinho.finalizarPedido(atendimento);

//        var listaCarrinho = fCarrinho.finalizarPedido(atendimento.getCliente().getId());
////        listaCarrinho.forEach(lista -> {
////            if(lista.getProduto() != null) {
////                var recibo = new Recibo(null, atendimento, lista.getProduto(), null, list., null);
////            }
////
////        });

        return null;
    }
}
