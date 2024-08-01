package com.example.my_chat.repository;

import com.example.my_chat.domain.solicitacao.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    Solicitacao getReferenceByUserRemetenteIdAndUserDestinatarioId(Long aLong, Long aLong1);

    List<Solicitacao> findAllByUserDestinatarioId(Long id);
}
