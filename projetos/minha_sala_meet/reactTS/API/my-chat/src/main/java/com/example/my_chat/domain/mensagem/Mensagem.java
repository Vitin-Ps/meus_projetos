package com.example.my_chat.domain.mensagem;

import com.example.my_chat.domain.conversa.Conversa;
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
    @JoinColumn(name = "user_remetente_id")
    private Usuario userRemetente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversa_id")
    private Conversa conversa;
    private String mensagem;
    private LocalDateTime data;

    public Mensagem(Usuario userRemetente, Conversa conversa, String mensagem, LocalDateTime data) {
        this.userRemetente = userRemetente;
        this.conversa = conversa;
        this.mensagem = mensagem;
        this.data = data;
    }
}
