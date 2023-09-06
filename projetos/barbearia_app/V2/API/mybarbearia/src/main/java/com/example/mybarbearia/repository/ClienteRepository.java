package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.cliente.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Page<Cliente> findByAtivoTrue(Pageable pageable); // devolve uma lista em Page com cliente que tenham ativo true

    Cliente getReferenceByIdAndAtivoTrue(Long id); // devolve o obejto cliente que tenha o id especificado e o ativo seja true

    boolean existsByIdAndAtivoTrue(Long idCliente); // mesma coisa, mas ele cha se existe e devolve um boolean
}
