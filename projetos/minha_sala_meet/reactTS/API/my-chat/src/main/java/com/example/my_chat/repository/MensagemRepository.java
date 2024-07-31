package com.example.my_chat.repository;

import com.example.my_chat.domain.mensagem.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findAllByGrupoIdOrderByData(Long id);

    void deleteAllByGrupoId(Long aLong);
}
