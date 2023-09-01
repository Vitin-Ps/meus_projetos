package com.example.mybarbearia.repository;

import com.example.mybarbearia.model.recibo.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReciboRepository extends JpaRepository<Recibo, Long> {
}
