package com.example.mybarbearia.model.carrinhodecompras;

import com.example.mybarbearia.infra.exception.ValidacaoExeption;
import com.example.mybarbearia.infra.services.StringEmMinutos;
import com.example.mybarbearia.model.atendimento.Atendimento;
import com.example.mybarbearia.model.carrinhodecompras.validacoes.ValidaCarrinhoComItem;
import com.example.mybarbearia.model.carrinhodecompras.validacoes.ValidaItemNoEstoque;
import com.example.mybarbearia.model.carrinhodecompras.validacoes.ValidadorFuncionalidadeCarrinhoDeCompras;
import com.example.mybarbearia.model.estoque.AlterarQuantidade;
import com.example.mybarbearia.model.estoque.DadosAtualizaEstoque;
import com.example.mybarbearia.model.produto.Produto;
import com.example.mybarbearia.model.recibo.DadosCadastroRecibo;
import com.example.mybarbearia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private List<ValidadorFuncionalidadeCarrinhoDeCompras> validadorCarrinho;


    public DadosDetalhamentoCarrinho addNoCarrinho(DadosCadastroCarrinho dados) {

        validadorCarrinho.forEach(validador -> {
            if (!(validador instanceof ValidaCarrinhoComItem) && !(validador instanceof ValidaItemNoEstoque)) {
                validador.checar(dados);
            }
        });

        // criando as Entidades baseado no id passado pelo dto
        var cliente = clienteRepository.findById(dados.idCliente()).get();
        var produto = retirarEstoque(dados);
        var servico = dados.idServico() != null ? servicoRepository.findById(dados.idServico()).get() : null;


        // fazendo a diferenciação de preço se for produto ou servico
        BigDecimal preco = null;
        if (dados.idProduto() != null) {
            preco = produto.getPreco();
        }
        if (dados.idServico() != null) {
            preco = servico.getPreco();
        }
        // criando o carrinho e salvando o banco
        var carrinho = new CarrinhoDeCompras(null, cliente, produto, servico, preco);
        carrinhoDeComprasRepository.save(carrinho);
        return new DadosDetalhamentoCarrinho(carrinho);
    }

    public List<DadosCadastroRecibo> finalizarPedido(Long idCliente) {
        validadorCarrinho.forEach(validador -> validador.checar(new DadosCadastroCarrinho(idCliente, null, null)));


        List<DadosCadastroRecibo> listaRecibos = new ArrayList<>();

        var produtos = carrinhoDeComprasRepository.produtosSelecionados(1L);
            System.out.println("Valos la: " + produtos);
            produtos.forEach(p -> {
               if(p != null) {
                   System.out.println("Produtos: " + p);
                   var quantidade = carrinhoDeComprasRepository.somarQuantidadeTotalProdutosByClienteIdAndProdutoId(idCliente, p);
                   var produto =  produtoRepository.getReferenceByIdAndAtivoTrue(p);
                   var preco = produto.getPreco().multiply(new BigDecimal(quantidade));

                   var recibo = new DadosCadastroRecibo(p, null, quantidade, null, preco);
                   System.out.println("produtos " + recibo);
                   listaRecibos.add(recibo);
               }
            });

        var servicos = carrinhoDeComprasRepository.servicosSelecionados(idCliente);
            System.out.println("Vamos la 2: " + servicos);
            servicos.forEach(s -> {
               if(s != null) {
                   var quantidade = carrinhoDeComprasRepository.somarQuantidadeTotalServicosByClienteIdAndServicoId(idCliente, s);
                   var servico = servicoRepository.getReferenceByIdAndAtivoTrue(s);
                   var preco = servico.getPreco().multiply(new BigDecimal(quantidade));
                   List<String> duracoes = new ArrayList<>();
                   for(int i = 0; i <= quantidade; i++) {
                       System.out.println(i);
                       duracoes.add(servico.getDuracao());
                   }
                   System.out.println(duracoes);
                   var date = StringEmMinutos.duracaoTotal(duracoes);


                   var recibo = new DadosCadastroRecibo(null, s, quantidade, StringEmMinutos.converterDateParaString(date), preco);
                   System.out.println("servicos " + recibo);
                   listaRecibos.add(recibo);
               }
            });



        System.out.println("lista Completa " + listaRecibos);

//        this.cancelarCarrinho(idCliente);

        return listaRecibos;
    }


    public Page<DadosListagemCarrinho> detalharCarrinho(Long idCliente, Pageable pageable) {
        validadorCarrinho.forEach(validador -> validador.checar(new DadosCadastroCarrinho(idCliente, null, null)));
        return carrinhoDeComprasRepository.findByClienteId(idCliente, pageable).map(DadosListagemCarrinho::new);
    }

    public void cancelarCarrinho(Long idCliente) { // essa função devolve os itens para o estoque e apaga o carrinho

        validadorCarrinho.forEach(validador -> validador.checar(new DadosCadastroCarrinho(idCliente, null, null)));

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

    private Produto retirarEstoque(DadosCadastroCarrinho dados) { // vai diminuir a quantidade do produto na tabela
        if (dados.idProduto() == null) {
            return null;
        }
        var estoque = estoqueRepository.getReferenceByProdutoId(dados.idProduto()); // idantifica o estoque baseado no id do produto
        if (estoque.getQuantidade() < 1) { // se quantidade do estoque for menor que 1
            throw new ValidacaoExeption("Produto está em Falta");
        }
        // diminuir a quantidade do estoque
        estoque.alterarQuantidade(new DadosAtualizaEstoque(null, 1, AlterarQuantidade.DIMINUIR));
        // retorna o produto
        return estoque.getProduto();
    }

    public void retirarCarrinho(DadosCadastroCarrinho dados) { // apada item do carrinho e devolve item para o estoque

        validadorCarrinho.forEach(validador -> validador.checar(dados));

        if (dados.idProduto() != null) {
            // vai pergar o primeiro resultado da consulta com o id cliente e o id produto
            var carrinho = carrinhoDeComprasRepository.findFirstByClienteIdAndProdutoId(dados.idCliente(), dados.idProduto());
            // deleta o dado
            carrinhoDeComprasRepository.deleteById(carrinho.getId());
            var estoque = estoqueRepository.getReferenceByProdutoId(dados.idProduto());
            // devolve o item para o estoque
            estoque.alterarQuantidade(new DadosAtualizaEstoque(null, 1, AlterarQuantidade.ADICIONAR));
        }
        if (dados.idServico() != null) {
            // faz o mesmo com o serviço
            var carrinho = carrinhoDeComprasRepository.findFirstByClienteIdAndServicoId(dados.idCliente(), dados.idServico());
            carrinhoDeComprasRepository.deleteById(carrinho.getId());
        }
    }
}
