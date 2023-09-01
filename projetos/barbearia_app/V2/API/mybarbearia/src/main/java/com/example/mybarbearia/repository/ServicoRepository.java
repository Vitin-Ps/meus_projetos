package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.servico.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    Page<Servico> findByAtivoTrue(Pageable pageable);

    Servico getReferenceByIdAndAtivoTrue(Long id);

    boolean existsByIdAndAtivoTrue(Long idServico);
}
