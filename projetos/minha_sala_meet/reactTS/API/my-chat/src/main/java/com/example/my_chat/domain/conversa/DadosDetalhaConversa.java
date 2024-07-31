package com.example.my_chat.domain.conversa;

public record DadosDetalhaConversa(
        Long id,
        String uuid
) {
    public DadosDetalhaConversa(Conversa conversa) {
        this(conversa.getId(), conversa.getUuid());
    }
}
