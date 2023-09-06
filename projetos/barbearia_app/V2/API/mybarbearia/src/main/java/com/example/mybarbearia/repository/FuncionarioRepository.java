package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.funcionario.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Page<Funcionario> findByAtivoTrue(Pageable pageable);

    Funcionario getReferenceByIdAndAtivoTrue(Long id);

    @Query("""
                SELECT f FROM Funcionario f
                WHERE
                    f.ativo = true
                    AND f.cargo = 'BARBEIRO'
                    AND f.id NOT IN(
                        SELECT a.funcionario.id FROM Atendimento a
                        WHERE a.data = :data
                    )
                ORDER BY FUNCTION('RAND')
                LIMIT 1
            """)
    Funcionario escolherFuncionarioComAgendaLivreNoHorario(LocalDateTime data); // prucura um fucnionario aleatorio que seja barbeiro e que não esteja com a dada igual a passada no parametro

    Boolean existsByIdAndAtivoTrue(Long aLong);
}
