package com.example.my_chat.domain.grupo;

import com.example.my_chat.domain.lista.Lista;
import com.example.my_chat.domain.usuario.Usuario;

public record DadosDetalhaGrupo(
        Long id,
        String nome,
        String uuid
) {
    public DadosDetalhaGrupo(Grupo grupo) {
        this(grupo.getId(), grupo.getNome(), grupo.getUuid());
    }

    public DadosDetalhaGrupo(Lista lista) {
        this(lista.getGrupo().getId(), lista.getGrupo().getNome(), lista.getGrupo().getUuid());
    }
}
