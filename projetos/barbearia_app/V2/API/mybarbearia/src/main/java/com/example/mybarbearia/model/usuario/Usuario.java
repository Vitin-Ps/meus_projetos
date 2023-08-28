package com.example.mybarbearia.model.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario{
    private String email;
    private String senha;
    private TipoUsuario tipoUsuario;
}
