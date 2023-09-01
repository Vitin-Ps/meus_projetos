package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.produto.Produto;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Page<Produto> findAllByAtivoTrue(Pageable pageable);

    Produto getReferenceByIdAndAtivoTrue(Long id);

    boolean existsByIdAndAtivoTrue(Long idProduto);
}
