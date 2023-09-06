package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.carrinhodecompras.CarrinhoDeCompras;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarrinhoDeComprasRepository extends JpaRepository<CarrinhoDeCompras, Long> {

    void deleteAllByClienteId(Long idCliente);

    @Query("""
            SELECT
                c.produto.id
            FROM
                CarrinhoDeCompras c
            WHERE
                c.cliente.id = :idCliente
            GROUP BY
                c.produto.id
            """)
    List<Long> produtosSelecionados(Long idCliente); // mostra os ids diferentes dos produtos que o cliente selecionou

    @Query("""
            SELECT
                c.servico.id
            FROM
                CarrinhoDeCompras c
            WHERE
                c.cliente.id = :idCliente
            GROUP BY
                c.servico.id
            """)
    List<Long> servicosSelecionados(Long idCliente);

    @Query("""
        SELECT COUNT(c.produto.id)
        FROM CarrinhoDeCompras c
        WHERE c.cliente.id = :idCliente AND c.produto.id = :idProduto
    """)
Integer somarQuantidadeTotalProdutosByClienteIdAndProdutoId(Long idCliente, Long idProduto); // faz um count(soma) dos dados que tenham o id do cliente e do produto conforma os parametros passados
        @Query("""
        SELECT COUNT(c.servico.id)
        FROM CarrinhoDeCompras c
        WHERE c.cliente.id = :idCliente AND c.servico.id = :idServico
    """)
Integer somarQuantidadeTotalServicosByClienteIdAndServicoId(Long idCliente, Long idServico);

    Page<CarrinhoDeCompras> findByClienteId(Long id, Pageable pageable); // devolve uma lista dos dados que tenham o id cliente conforme os parametros

    CarrinhoDeCompras findFirstByClienteIdAndProdutoId(Long idCliente, Long idProduto); // paega o primeiro resultado da consulta de dados com o id cliente e id produto especificados
    CarrinhoDeCompras findFirstByClienteIdAndServicoId(Long idCliente, Long idServico);

    boolean existsByClienteId(Long idCliente); // se existe algum dado com o id Cliente especificado

    boolean existsByClienteIdAndProdutoId(Long idCliente, Long idProduto); // mesma coisa so que com produto

    boolean existsByClienteIdAndServicoId(Long idCliente, Long idServico); // mesma coisa so que com servico
}
