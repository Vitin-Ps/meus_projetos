package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.recibo.DadosCadastroRecibo;
import com.example.mybarbearia.model.recibo.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReciboRepository extends JpaRepository<Recibo, Long> {
    List<Recibo> getReferenceByAtendimentoId(Long idAtendimento);
}
