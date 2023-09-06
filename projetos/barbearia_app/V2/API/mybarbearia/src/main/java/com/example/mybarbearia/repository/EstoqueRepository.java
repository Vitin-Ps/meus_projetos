package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.estoque.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Estoque getReferenceByProdutoId(Long idProduto);

}
