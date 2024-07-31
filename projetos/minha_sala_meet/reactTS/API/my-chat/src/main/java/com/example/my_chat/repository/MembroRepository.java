package com.example.my_chat.repository;

import com.example.my_chat.domain.membro.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MembroRepository extends JpaRepository<Membro, Long> {
    List<Membro> findAllByUsuarioId(Long id);

    List<Membro> findAllByGrupoId(Long id);

    Membro getReferenceByUsuarioIdAndGrupoId(Long userId, Long grupoId);

    void deleteAllByGrupoId(Long aLong);
}
