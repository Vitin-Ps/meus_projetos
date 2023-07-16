package com.projetos.login.model.usuario;

import java.util.Objects;

public class Usuario {
    private String login;
    private String senha;


    public Usuario(DadosAberturaLogin dados) {
        this.login = dados.login();
        this.senha = dados.senha();
    }
    public Boolean ehIgual(String login, String senha) {
        if(!this.login.equals(login) || !this.senha.equals(senha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(login, usuario.login) && Objects.equals(senha, usuario.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, senha);
    }

    //----- Gets e Sets

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
