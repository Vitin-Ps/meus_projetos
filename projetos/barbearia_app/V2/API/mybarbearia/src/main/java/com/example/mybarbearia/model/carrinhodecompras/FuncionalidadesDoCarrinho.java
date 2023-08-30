package com.example.mybarbearia.model.carrinhodecompras;

import com.example.mybarbearia.exception.ValidacaoExeption;
import com.example.mybarbearia.model.produto.Produto;
import com.example.mybarbearia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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


    public DadosDetalhamentoCarrinho addNoCarrinho(DadosCadastroCarrinho dados) {
        // checando se as Entidades existem
        if (dados.idCliente() != null && !clienteRepository.existsById(dados.idCliente())) {
            throw new ValidacaoExeption("Cliente não Existe");
        }
        if (dados.idProduto() != null && !produtoRepository.existsById(dados.idCliente())) {
            throw new ValidacaoExeption("Produto não Existe");
        }
        if (dados.idServico() != null && !servicoRepository.existsById(dados.idCliente())) {
            throw new ValidacaoExeption("Serviço não Existe");
        }
       // criando as Entidades baseado no id passado pelo dto
        System.out.println("Passou1");
        var cliente = clienteRepository.findById(dados.idCliente()).get();
        System.out.println("Passou2");
        var produto = checarQuantidade(dados);
        System.out.println("Passou3");
        var servico = servicoRepository.findById(dados.idCliente()).orElse(null);
        System.out.println("servico " + servico);


        // fazendo a diferenciação de preço se for produto ou servico
        BigDecimal preco = null;
        if(dados.idProduto() != null) {
            preco = produto.getPreco();
        }
        if(dados.idServico() != null) {
            preco = servico.getPreco();
        }
        System.out.println("Passou4");
        // criando o carrinho e salvando o banco
        var carrinho = new CarrinhoDeCompras(null, cliente, produto, servico, preco);
        carrinhoDeComprasRepository.save(carrinho);
        System.out.println("Passou5");
        return new DadosDetalhamentoCarrinho(carrinho);
    }

    public Produto checarQuantidade(DadosCadastroCarrinho dados) {
        if(dados.idProduto() == null) {
            return null;
        }
        var estoque = estoqueRepository.findByProdutoId(dados.idProduto());
        if(estoque.getQuantidade() < 1) {
            throw new ValidacaoExeption("Produto em falta no Estoque");
        }
        return estoque.getProduto();
    }
}