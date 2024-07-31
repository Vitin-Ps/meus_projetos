package com.example.my_chat.domain.conversa;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tbl_conversa")
@Entity(name = "Conversa")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Conversa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private TipoConversa tipo;

    public Conversa(String uuid, TipoConversa tipoConversa) {
        this.uuid = uuid;
        this.tipo = tipoConversa;
    }

    public void alterar(String uuid) {
        if(!StringUtils.isBlank(uuid)) {
            this.uuid = uuid;
        }
    }
}
