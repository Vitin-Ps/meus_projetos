package com.example.mybarbearia.model.carrinhodecompras.validacoes;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.model.carrinhodecompras.DadosCadastroCarrinho;
import com.example.mybarbearia.repository.CarrinhoDeComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaItemNoEstoque implements ValidadorFuncionalidadeCarrinhoDeCompras{
    @Autowired
    CarrinhoDeComprasRepository carrinhoDeComprasRepository;

    @Override
    public void checar(DadosCadastroCarrinho dados) { // checa se item existe no banco de dados do carrinho
        if(dados.idProduto() != null && !carrinhoDeComprasRepository.existsByClienteIdAndProdutoId(dados.idCliente(), dados.idProduto())) {
            throw new ValidacaoExeption("Produto não está no carrinho");
        }
        if(dados.idServico() != null && !carrinhoDeComprasRepository.existsByClienteIdAndServicoId(dados.idCliente(), dados.idServico())) {
            throw new ValidacaoExeption("Serviço não está no carrinho");
        }
    }
}
