package com.example.mybarbearia.model.usuario;

public enum TipoUsuario {
    ADMIN("admin"),
    ClIENTE("cliente"),
    BARBEIRO("barbeiro"),
    ATENDENTE("atendente");

    private String tipoUsuario;

    TipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

}
