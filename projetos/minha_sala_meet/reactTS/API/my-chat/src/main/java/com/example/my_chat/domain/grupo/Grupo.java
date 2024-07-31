package com.example.my_chat.domain.grupo;

import com.example.my_chat.domain.conversa.Conversa;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tbl_grupo")
@Entity(name = "Grupo")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagem;
    private String nome;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversa_id")
    private Conversa conversa;

    public Grupo(String nome, Conversa conversa) {
        this.nome = nome;
        this.conversa = conversa;
    }

    public void alterar(String imagem, String nome) {
        if(!StringUtils.isBlank(nome)) {
            this.nome = nome;
        }
        if(!StringUtils.isBlank(imagem)) {
            this.imagem = imagem;
        }
    }
}
