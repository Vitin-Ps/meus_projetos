package com.example.my_chat.repository;

import com.example.my_chat.domain.lista.Lista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ListaRepository extends JpaRepository<Lista, Long> {
    List<Lista> findAllByUsuarioId(Long id);
}
