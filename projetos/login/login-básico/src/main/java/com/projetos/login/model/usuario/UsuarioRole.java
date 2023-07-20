package com.projetos.login.model.usuario;

public enum UsuarioRole {
    ADMIN("adm"),
    USER("comum");

    private String role;

    UsuarioRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
