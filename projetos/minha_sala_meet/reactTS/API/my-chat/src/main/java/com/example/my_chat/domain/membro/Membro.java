package com.example.my_chat.domain.membro;

import com.example.my_chat.domain.grupo.Grupo;
import com.example.my_chat.domain.usuario.TipoUsuario;
import com.example.my_chat.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tbl_membro")
@Entity(name = "Membro")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Membro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;
    @Enumerated(EnumType.STRING)
    private TipoUsuario cargo;

    public Membro(Usuario usuario, Grupo grupo, TipoUsuario cargo) {
        this.usuario = usuario;
        this.grupo = grupo;
        this.cargo = cargo;
    }

    public void alterarSituacao(TipoUsuario cargo) {
            this.cargo = cargo;
    }
}
