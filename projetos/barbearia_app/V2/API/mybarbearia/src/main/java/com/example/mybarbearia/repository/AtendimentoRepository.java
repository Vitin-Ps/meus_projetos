package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.atendimento.Atendimento;
import com.example.mybarbearia.model.atendimento.StatusAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    boolean existsByData(LocalDateTime data);

    List<Atendimento> findAllByFuncionarioIdAndStatus(Long idFuncionario, StatusAtendimento statusAtendimento);

}
