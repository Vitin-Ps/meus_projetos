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

    public void cancelarCarrinho(Long idCliente) {

        var produtosSelecionados = carrinhoDeComprasRepository.produtosSelecionados(idCliente);
        System.out.println("chegou1");
        if(produtosSelecionados.isEmpty()) {
            throw new ValidacaoExeption("Cliente não encontrado");
        }
        System.out.println(produtosSelecionados);
        System.out.println("chegou2");
        produtosSelecionados.forEach(produtos -> {
            if (produtos == null) {
                return;
            }
            var quantidadeNoCarrinho = carrinhoDeComprasRepository.somarQuantidadeTotalProdutosByClienteIdAndProdutoId(idCliente, produtos);
            var estoque = estoqueRepository.getReferenceByProdutoId(produtos);
            System.out.println(quantidadeNoCarrinho);
            estoque.alterarQuantidade(new DadosAtualizaEstoque(null, quantidadeNoCarrinho, AlterarQuantidade.ADICIONAR));
        });
        carrinhoDeComprasRepository.deleteAllByClienteId(idCliente);
    }

    public Produto retirarEstoque(DadosCadastroCarrinho dados) {
        if(dados.idProduto() == null) {
            return null;
        }
        var estoque = estoqueRepository.getReferenceByProdutoId(dados.idProduto());
        if(estoque.getQuantidade() < 1) {
            throw new ValidacaoExeption("Produto está em Falta");
        }
        estoque.alterarQuantidade(new DadosAtualizaEstoque(null,1, AlterarQuantidade.DIMINUIR));
        return estoque.getProduto();
    }

    public void retirarCarrinho(DadosCadastroCarrinho dados) {
        if(dados.idCliente() != null) {
            if(dados.idProduto() != null) {
                System.out.println("deucerto " + dados.idCliente() + dados.idProduto());
               var carrinho =  carrinhoDeComprasRepository.findFirstByClienteIdAndProdutoId(dados.idCliente(),dados.idProduto());
                carrinhoDeComprasRepository.deleteById(carrinho.getId());
                var estoque = estoqueRepository.getReferenceByProdutoId(dados.idProduto());
                estoque.alterarQuantidade(new DadosAtualizaEstoque(null, 1, AlterarQuantidade.ADICIONAR));
            }
            if(dados.idServico() != null) {
                System.out.println("intrometido");
                var carrinho = carrinhoDeComprasRepository.findFirstByClienteIdAndServicoId(dados.idCliente(), dados.idServico());
                carrinhoDeComprasRepository.deleteById(carrinho.getId());
            }


        }
    }
}