package com.example.my_chat.domain.amigo;

import com.example.my_chat.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tbl_amigo")
@Entity(name = "Amigo")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Amigo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amigo_id")
    private Usuario amigo;
    public Amigo(Usuario usuario, Usuario amigo) {
        this.usuario = usuario;
        this.amigo = amigo;
    }

}
