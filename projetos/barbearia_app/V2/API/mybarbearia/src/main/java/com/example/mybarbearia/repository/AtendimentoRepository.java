package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.atendimento.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
}
