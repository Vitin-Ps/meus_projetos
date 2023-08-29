package com.example.mybarbearia.model.carrinhodecompras;

import com.example.mybarbearia.exception.ValidacaoExeption;
import com.example.mybarbearia.model.produto.Produto;
import com.example.mybarbearia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdicionarNoCarrinho {
    @Autowired
    CarrinhoDeComprasRepository carrinhoDeComprasRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ServicoRepository servicoRepository;
    @Autowired
    EstoqueRepository estoqueRepository;


    public DadosDetalhamentoCarrinho agendar(DadosCadastroCarrinho dados) {
        if (!clienteRepository.existsById(dados.idCliente())) {
            throw new ValidacaoExeption("Cliente não Existe");
        }
        if (!produtoRepository.existsById(dados.idCliente())) {
            throw new ValidacaoExeption("Produto não Existe");
        }
        if (!servicoRepository.existsById(dados.idCliente())) {
            throw new ValidacaoExeption("Serviço não Existe");
        }

        return null;
    }

    public Produto checarQuantidade(DadosCadastroCarrinho dados) {
        var estoque = estoqueRepository.findByIdProduto(dados.idProduto());
        if(dadosestoque.getQuantidade() < 1) {
            throw new ValidacaoExeption("Produto em falta no Estoque");
        }
        return estoque.getProduto();
    }
}