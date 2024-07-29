package com.example.my_chat.domain.lista;

import com.example.my_chat.domain.grupo.Grupo;
import com.example.my_chat.domain.usuario.TipoUsuario;
import com.example.my_chat.domain.usuario.Usuario;

public record DadosDetalhaLista(
        Long id,
        Usuario usuario,
        Grupo grupo,
        TipoUsuario cargo
) {
    public DadosDetalhaLista(Lista lista) {
        this(lista.getId(), lista.getUsuario(), lista.getGrupo(), lista.getCargo());
    }
}
