package com.example.mybarbearia.model.carrinhodecompras;

import com.example.mybarbearia.exception.ValidacaoExeption;
import com.example.mybarbearia.model.estoque.AlterarQuantidade;
import com.example.mybarbearia.model.estoque.DadosAtualizaEstoque;
import com.example.mybarbearia.model.produto.Produto;
import com.example.mybarbearia.model.recibo.DadosCriacaoRecibo;
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
        var produto = checarQuantidade(dados);
        var servico = servicoRepository.findById(dados.idCliente()).orElse(null);


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

    public void limparCarrinho() {
        Long id = 3L;
        carrinhoDeComprasRepository.deleteAllByClienteId(id);
    }

    public void finalizarCarrinho(Long idCliente) {
//

        var produtosSelecionados = carrinhoDeComprasRepository.produtosSelecionados(idCliente);
        produtosSelecionados.forEach(produtos -> {
            System.out.println("produto aqeui : " + produtos);
            var quantidadeNoCarrinho = carrinhoDeComprasRepository.somarQuantidadeTotalProdutosByClienteIdAndProdutoId(idCliente, produtos);
            System.out.println("quantidade Produto: " + quantidadeNoCarrinho);

            var estoque = estoqueRepository.getReferenceByProdutoId(produtos);
            estoque.alterarQuantidade(new DadosAtualizaEstoque(estoque.getId(), quantidadeNoCarrinho, AlterarQuantidade.DIMINUIR));

        });

////        this.limparCarrinho();
//    }


//    public DadosCriacaoRecibo dadosPeloIdCliente(Long id) {
//        var listaCarrinho = carrinhoDeComprasRepository.findByClienteId(id);
//    }


    }

    public Produto checarQuantidade(DadosCadastroCarrinho dados) {
        if(dados.idProduto() == null) {
            return null;
        }
        var estoque = estoqueRepository.getReferenceByProdutoId(dados.idProduto());
        if(estoque.getQuantidade() < 1) {
            throw new ValidacaoExeption("Produto está em Falta");
        }
        return estoque.getProduto();
    }
}