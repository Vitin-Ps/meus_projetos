package com.example.my_chat.domain.amigo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AmigoRespository extends JpaRepository<Amigo, Long> {
    Amigo getReferenceByUserIdAndAmigoId(Long aLong, Long aLong1);

    List<Amigo> findAllByUserId(Long id);
}
