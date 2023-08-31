package com.example.mybarbearia.model.carrinhodecompras;

import com.example.mybarbearia.exception.ValidacaoExeption;
import com.example.mybarbearia.model.estoque.AlterarQuantidade;
import com.example.mybarbearia.model.estoque.DadosAtualizaEstoque;
import com.example.mybarbearia.model.produto.Produto;
import com.example.mybarbearia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FuncionalidadesDoCarrinho {
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
        if (dados.idProduto() != null && !produtoRepository.existsById(dados.idProduto())) {
            throw new ValidacaoExeption("Produto não Existe");
        }
        if (dados.idServico() != null && !servicoRepository.existsById(dados.idServico())) {
            throw new ValidacaoExeption("Serviço não Existe");
        }
       // criando as Entidades baseado no id passado pelo dto
        var cliente = clienteRepository.findById(dados.idCliente()).get();
        var produto = retirarEstoque(dados);
        var servico = dados.idServico() != null ? servicoRepository.findById(dados.idServico()).get() : null;


        // fazendo a diferenciação de preço se for produto ou servico
        BigDecimal preco = null;
        if(dados.idProduto() != null) {
            preco = produto.getPreco();
        }
        if(dados.idServico() != null) {
            preco = servico.getPreco();
        }
        // criando o carrinho e salvando o banco
        var carrinho = new CarrinhoDeCompras(null, cliente, produto, servico, preco);
        carrinhoDeComprasRepository.save(carrinho);
        return new DadosDetalhamentoCarrinho(carrinho);
    }

    public void cancelarCarrinho(Long idCliente) { // essa função devolve os itens para o estoque e apaga o carrinho
        if(idCliente == null || !carrinhoDeComprasRepository.existsByClienteId(idCliente)) {
            throw new RuntimeException("Seu carrinho está Vazio");
        }
        var produtosSelecionados = carrinhoDeComprasRepository.produtosSelecionados(idCliente); // coleta od id dos produtos selecionados
        produtosSelecionados.forEach(produtos -> { // vai realizar o código baseado em cada id de produto
            if (produtos == null) {
                return; // para o looping
            }
            // faz uma soma de quantos dados com i mesmo id de produto tem
            var quantidadeNoCarrinho = carrinhoDeComprasRepository.somarQuantidadeTotalProdutosByClienteIdAndProdutoId(idCliente, produtos);
            //chama o estoque baseado no id de produto do lopping
            var estoque = estoqueRepository.getReferenceByProdutoId(produtos);
            // pega a quantidade que tem no carrinho e adiciona de novo no estoque
            estoque.alterarQuantidade(new DadosAtualizaEstoque(null, quantidadeNoCarrinho, AlterarQuantidade.ADICIONAR));
        });
        // deletar todos os dados do carrinho baseado no id do cliente
        carrinhoDeComprasRepository.deleteAllByClienteId(idCliente);
    }

    public Produto retirarEstoque(DadosCadastroCarrinho dados) { // vai diminuir a quantidade do produto na tabela
        if(dados.idProduto() == null) {
            return null;
        }
        var estoque = estoqueRepository.getReferenceByProdutoId(dados.idProduto()); // idantifica o estoque baseado no id do produto
        if(estoque.getQuantidade() < 1) { // se quantidade do estoque for menor que 1
            throw new ValidacaoExeption("Produto está em Falta");
        }
        // diminuir a quantidade do estoque
        estoque.alterarQuantidade(new DadosAtualizaEstoque(null,1, AlterarQuantidade.DIMINUIR));
        // retorna o produto
        return estoque.getProduto();
    }

    public void retirarCarrinho(DadosCadastroCarrinho dados) { // apada item do carrinho e devolve item para o estoque
        if(dados.idCliente() != null) {
            if(dados.idProduto() != null) {
                // vai pergar o primeiro resultado da consulta com o id cliente e o id produto
                var carrinho =  carrinhoDeComprasRepository.findFirstByClienteIdAndProdutoId(dados.idCliente(),dados.idProduto());
               // deleta o dado
               carrinhoDeComprasRepository.deleteById(carrinho.getId());
                var estoque = estoqueRepository.getReferenceByProdutoId(dados.idProduto());
                // devolve o item para o estoque
                estoque.alterarQuantidade(new DadosAtualizaEstoque(null, 1, AlterarQuantidade.ADICIONAR));
            }
            if(dados.idServico() != null) {
                // faz o mesmo com o serviço
                var carrinho = carrinhoDeComprasRepository.findFirstByClienteIdAndServicoId(dados.idCliente(), dados.idServico());
                carrinhoDeComprasRepository.deleteById(carrinho.getId());
            }
        }
    }
}