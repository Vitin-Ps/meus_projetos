package com.example.mybarbearia.model.carrinhodecompras.validacoes;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.model.carrinhodecompras.DadosCadastroCarrinho;
import com.example.mybarbearia.repository.CarrinhoDeComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaCarrinhoComItem implements ValidadorFuncionalidadeCarrinhoDeCompras{
    @Autowired
    CarrinhoDeComprasRepository carrinhoDeComprasRepository;
    @Override
    public void checar(DadosCadastroCarrinho dados) {
        if(dados.idCliente() != null && !carrinhoDeComprasRepository.existsByClienteId(dados.idCliente())) { //se o id for diferente de null e não existir no db
            throw new ValidacaoExeption("Seu Carrinho está vazio");
        }
    }
}
