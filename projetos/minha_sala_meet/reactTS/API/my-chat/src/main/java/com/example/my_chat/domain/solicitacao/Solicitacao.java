package com.example.my_chat.domain.solicitacao;

import com.example.my_chat.domain.conversa.Conversa;
import com.example.my_chat.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tbl_solicitacao")
@Entity(name = "Solicitacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_remetente_id")
    private Usuario userRemetente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_destinatario_id")
    private Usuario userDestinatario;
    public Solicitacao(Usuario userRemetente, Usuario userDestinatario) {
        this.userRemetente = userRemetente;
        this.userDestinatario = userDestinatario;
    }

}
