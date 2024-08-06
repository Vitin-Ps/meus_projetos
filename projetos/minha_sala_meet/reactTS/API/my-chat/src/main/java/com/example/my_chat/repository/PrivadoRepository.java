package com.example.my_chat.repository;

import com.example.my_chat.domain.grupo.DadosDetalhaGrupo;
import com.example.my_chat.domain.privado.Privado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivadoRepository extends JpaRepository<Privado, Long> {

    List<Privado> findAllByUserOneId(Long id);


    Privado getReferenceByUserOneIdAndUserTwoId(Long s, Long aLong);

    Privado getReferenceByConversaIdAndUserOneId(Long conversa_id, Long user_one_id);
}
