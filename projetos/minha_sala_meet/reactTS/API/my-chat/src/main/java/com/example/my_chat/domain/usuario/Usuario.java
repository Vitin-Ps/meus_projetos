package com.example.my_chat.domain.usuario;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Table(name = "tbl_usuario")
@Entity(name = "TblUsuario")
@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String imagem;
    private String login;
    private String senha;
    private Boolean ativo;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    public Usuario(String nome, String login, String senhaCodificada, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.login = login;
        this.senha = senhaCodificada;
        this.tipoUsuario = tipoUsuario;
        this.ativo = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.tipoUsuario == TipoUsuario.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        if(this.tipoUsuario == TipoUsuario.USER) return List.of( new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void atualizarInfo(String email, String senhaCodificada) {
        if(!StringUtils.isBlank(email)) {
            this.login = email;
        }
        if(!StringUtils.isBlank(senhaCodificada)) {
            this.senha = senhaCodificada;
        }
    }
}
