package com.projetos.base.repositories;

import com.projetos.base.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);

//    @Query("SELECT * FROM users WHERE login = :login")
//    User getReferenceByLogin(String login);

}
