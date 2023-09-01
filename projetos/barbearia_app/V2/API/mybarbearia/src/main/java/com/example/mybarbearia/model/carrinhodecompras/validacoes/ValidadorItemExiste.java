package com.example.mybarbearia.model.carrinhodecompras.validacoes;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.model.carrinhodecompras.DadosCadastroCarrinho;
import com.example.mybarbearia.repository.ProdutoRepository;
import com.example.mybarbearia.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorItemExiste implements ValidadorFuncionalidadeCarrinhoDeCompras{
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ServicoRepository servicoRepository;

    @Override
    public void checar(DadosCadastroCarrinho dados) {
        if(dados.idProduto() != null && !produtoRepository.existsByIdAndAtivoTrue(dados.idProduto())) {
            throw new ValidacaoExeption("Produto não cadastrado");
        }
        if(dados.idServico() != null && !servicoRepository.existsByIdAndAtivoTrue(dados.idServico())) {
            throw new ValidacaoExeption("Serviço não cadastrado");
        }
    }
}
