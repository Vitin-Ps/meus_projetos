package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.funcionario.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Page<Funcionario> findByAtivoTrue(Pageable pageable);

    Funcionario getReferenceByIdAndAtivoTrue(Long id);
}
