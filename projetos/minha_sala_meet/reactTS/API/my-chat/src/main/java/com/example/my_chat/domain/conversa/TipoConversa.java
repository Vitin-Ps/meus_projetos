package com.example.my_chat.domain.conversa;

public enum TipoConversa {
    PRIVADO("privado"),
    GRUPO("grupo");

    private String tipoConversa;

    TipoConversa(String tipoConversa) {
        this.tipoConversa = tipoConversa;
    }

    public String getTipoConversa() {
        return tipoConversa;
    }

}
