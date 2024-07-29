package com.example.my_chat.domain.mensagem;

import com.example.my_chat.domain.grupo.DadosDetalhaGrupo;
import com.example.my_chat.domain.grupo.Grupo;
import com.example.my_chat.domain.usuario.DadosDetalhamentoUser;
import com.example.my_chat.domain.usuario.TipoUsuario;
import com.example.my_chat.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosDetalhaMensagem(
        Long id,
        DadosDetalhamentoUser usuario,
        DadosDetalhaGrupo grupo,
        String mensagem,
        LocalDateTime data
) {
    public DadosDetalhaMensagem(Mensagem mensagem) {
        this(mensagem.getId(), new DadosDetalhamentoUser(mensagem.getUsuario()), new DadosDetalhaGrupo(mensagem.getGrupo()), mensagem.getMensagem(), mensagem.getData());
    }
}
