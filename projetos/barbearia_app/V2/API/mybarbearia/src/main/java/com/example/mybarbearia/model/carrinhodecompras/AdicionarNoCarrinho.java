package com.example.mybarbearia.model.carrinhodecompras;

import com.example.mybarbearia.exception.ValidacaoExeption;
import com.example.mybarbearia.repository.CarrinhoDeComprasRepository;
import com.example.mybarbearia.repository.ClienteRepository;
import com.example.mybarbearia.repository.ProdutoRepository;
import com.example.mybarbearia.repository.ServicoRepository;
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


    public DadosListagemCarrinho agendar(DadosCadastroCarrinho dados) {
        if (!clienteRepository.existsById(dados.idCliente())) {
            throw new ValidacaoExeption("Cliente não Existe");
        }
        if (dados.tipoItem() == TipoItem.PRODUTO && !produtoRepository.existsById(dados.idCliente())) {
            throw new ValidacaoExeption("Produto não Existe");
        }
        if (dados.tipoItem() == TipoItem.SERVICO && !servicoRepository.existsById(dados.idCliente())) {
            throw new ValidacaoExeption("Serviço não Existe");
        }

        return null;
    }
}