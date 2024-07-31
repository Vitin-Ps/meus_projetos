package com.example.my_chat.domain.privado;

import com.example.my_chat.domain.conversa.Conversa;
import com.example.my_chat.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@Table(name = "tbl_privado")
@Entity(name = "Privado")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Privado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_one_id")
    private Usuario userOne;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_two_id")
    private Usuario userTwo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversa_id")
    private Conversa conversa;
    public Privado(Usuario userOne, Usuario userTwo, Conversa conversa) {
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.conversa = conversa;
    }

}
