package com.example.mybarbearia.model.carrinhodecompras.validacoes;

import com.example.mybarbearia.exception.ValidacaoExeption;
import com.example.mybarbearia.model.carrinhodecompras.DadosCadastroCarrinho;
import com.example.mybarbearia.repository.CarrinhoDeComprasRepository;
import com.example.mybarbearia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaCliente implements ValidadorFuncionalidadeCarrinhoDeCompras{
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    CarrinhoDeComprasRepository carrinhoDeComprasRepository;
    @Override
    public void checar(DadosCadastroCarrinho dados) {

        if(dados.idCliente() == null) {
            throw new ValidacaoExeption("Id do Cliente está nulo");
        }
        if(!clienteRepository.existsByIdAndAtivoTrue(dados.idCliente())) {
            throw new ValidacaoExeption("Cliente não existe");
        }
    }
}
