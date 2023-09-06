package com.example.mybarbearia.model.carrinhodecompras.validacoes;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.model.carrinhodecompras.DadosCadastroCarrinho;
import org.springframework.stereotype.Component;

@Component
public class ValidaDoisTiposDeItem implements ValidadorFuncionalidadeCarrinhoDeCompras{

    @Override
    public void checar(DadosCadastroCarrinho dados) {
        if(dados.idProduto() != null && dados.idServico() != null) throw  new ValidacaoExeption("Não pode ser passado dois tipos de itens ao mesmo tempo");
    }
}
