package com.example.my_chat.domain.grupo;

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
    private String uuid;
    private String nome;

    public Grupo(String uuid, String nome) {
        this.nome = nome;
        this.uuid = uuid;
    }

    public void alterar(String uuid, String nome) {
        if(!StringUtils.isBlank(nome)) {
            this.nome = nome;
        }
        if(!StringUtils.isBlank(uuid)) {
            this.uuid = uuid;
        }
    }
}
