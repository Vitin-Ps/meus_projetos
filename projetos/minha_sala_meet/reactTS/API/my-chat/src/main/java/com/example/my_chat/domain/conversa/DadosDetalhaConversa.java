package com.example.my_chat.domain.conversa;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record DadosDetalhaConversa(
        Long id,
        String uuid,
        @Enumerated(EnumType.STRING)
        TipoConversa tipo
) {
    public DadosDetalhaConversa(Conversa conversa) {
        this(conversa.getId(), conversa.getUuid(), conversa.getTipo());
    }
}
