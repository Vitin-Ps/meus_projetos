package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.cliente.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Page<Cliente> findByAtivoTrue(Pageable pageable);

    Cliente getReferenceByIdAndAtivoTrue(Long id);

    boolean existsByIdAndAtivoTrue(Long idCliente);
}
