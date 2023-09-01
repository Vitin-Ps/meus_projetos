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
    List<Long> produtosSelecionados(Long idCliente);

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
Integer somarQuantidadeTotalProdutosByClienteIdAndProdutoId(Long idCliente, Long idProduto);
        @Query("""
        SELECT COUNT(c.servico.id)
        FROM CarrinhoDeCompras c
        WHERE c.cliente.id = :idCliente AND c.servico.id = :idServico
    """)
Integer somarQuantidadeTotalServicosByClienteIdAndServicoId(Long idCliente, Long idServico);

    CarrinhoDeCompras getReferenceByClienteId(Long idCliente);

    Page<CarrinhoDeCompras> findByClienteId(Long id, Pageable pageable);
    List<CarrinhoDeCompras> findAllByClienteId(Long id);

    CarrinhoDeCompras findFirstByClienteIdAndProdutoId(Long idCliente, Long idProduto);
    CarrinhoDeCompras findFirstByClienteIdAndServicoId(Long idCliente, Long idServico);

    boolean existsByClienteId(Long idCliente);

    boolean existsByClienteIdAndProdutoId(Long idCliente, Long idProduto);

    boolean existsByClienteIdAndServicoId(Long idCliente, Long idServico);
}
