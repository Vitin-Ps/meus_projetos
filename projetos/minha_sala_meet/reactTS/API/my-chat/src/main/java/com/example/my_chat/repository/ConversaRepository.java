package com.example.my_chat.repository;

import com.example.my_chat.domain.conversa.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversaRepository extends JpaRepository<Conversa, Long> {

}
