package com.example.my_chat.domain.usuario;

public enum TipoUsuario {
    ADMIN("admin"),
    USER("user");

    private String tipoUsuario;

    TipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
}
