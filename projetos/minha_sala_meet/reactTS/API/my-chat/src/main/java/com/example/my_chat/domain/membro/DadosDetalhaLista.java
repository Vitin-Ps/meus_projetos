package com.example.my_chat.domain.membro;

import com.example.my_chat.domain.grupo.DadosDetalhaGrupo;
import com.example.my_chat.domain.usuario.DadosDetalhamentoUser;
import com.example.my_chat.domain.usuario.TipoUsuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record DadosDetalhaLista(
        Long id,
        DadosDetalhamentoUser usuario,
        DadosDetalhaGrupo grupo,
        @Enumerated(EnumType.STRING)
        TipoUsuario cargo
) {
    public DadosDetalhaLista(Membro lista) {
        this(lista.getId(), new DadosDetalhamentoUser(lista.getUsuario()), new DadosDetalhaGrupo(lista.getGrupo()), lista.getCargo());
    }
}
