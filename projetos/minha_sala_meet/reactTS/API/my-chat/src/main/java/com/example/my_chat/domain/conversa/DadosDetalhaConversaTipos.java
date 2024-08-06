package com.example.my_chat.domain.conversa;

import com.example.my_chat.domain.grupo.DadosDetalhaGrupo;
import com.example.my_chat.domain.privado.DadosDetalhaPrivado;
import com.example.my_chat.domain.privado.Privado;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record DadosDetalhaConversaTipos(
    Long id,
    DadosDetalhaGrupo grupo,
    DadosDetalhaPrivado privado,
    @Enumerated(EnumType.STRING)
    TipoConversa tipo
) {
    public DadosDetalhaConversaTipos(Privado privado) {
        this(privado.getConversa().getId(), null, new DadosDetalhaPrivado(privado), privado.getConversa().getTipo());
    }

    public DadosDetalhaConversaTipos(DadosDetalhaGrupo grupo) {
        this(grupo.conversa().id(), grupo, null, grupo.conversa().tipo());
    }
}
