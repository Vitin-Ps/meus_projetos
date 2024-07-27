package com.example.my_chat.repository;

import com.example.crudjava.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String subject);

    Usuario getReferenceByLogin(String email);
}
