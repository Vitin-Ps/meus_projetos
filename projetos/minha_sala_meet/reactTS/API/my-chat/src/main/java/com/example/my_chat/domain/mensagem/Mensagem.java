package com.example.my_chat.domain.mensagem;

import com.example.my_chat.domain.grupo.Grupo;
import com.example.my_chat.domain.usuario.TipoUsuario;
import com.example.my_chat.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "tbl_mensagem")
@Entity(name = "Mensagem")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;
    private String mensagem;
    private LocalDateTime data;

    public Mensagem(Usuario usuario, Grupo grupo, String mensagem, LocalDateTime data) {
        this.usuario = usuario;
        this.grupo = grupo;
        this.mensagem = mensagem;
        this.data = data;
    }
}
