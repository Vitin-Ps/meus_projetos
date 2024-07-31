package com.example.my_chat.domain.grupo;

import com.example.my_chat.domain.conversa.DadosDetalhaConversa;
import com.example.my_chat.domain.membro.Membro;

public record DadosDetalhaGrupo(
        Long id,
        String nome,
        String imagem,
        DadosDetalhaConversa conversa
) {
    public DadosDetalhaGrupo(Grupo grupo) {
        this(grupo.getId(), grupo.getNome(), grupo.getImagem(), new DadosDetalhaConversa(grupo.getConversa()));
    }

    public DadosDetalhaGrupo(Membro membro) {
        this(membro.getGrupo().getId(), membro.getGrupo().getNome(), membro.getGrupo().getImagem(), new DadosDetalhaConversa(membro.getGrupo().getConversa()));
    }
}
