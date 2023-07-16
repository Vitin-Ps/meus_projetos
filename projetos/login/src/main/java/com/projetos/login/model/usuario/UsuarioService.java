package com.projetos.login.model.usuario;

import com.projetos.login.model.DAO.Conexao;
import com.projetos.login.model.DAO.RegraDeNegocioException;
import com.projetos.login.model.DAO.UsuarioDAO;

import java.sql.Connection;
import java.util.Set;

public class UsuarioService {
    private Conexao connection; // Aqui está sendo criado um atributo da classe para armazenar a conexão criada

//    private Set<Conta> contas = new HashSet<>(); // criando uma lista(array) do tipo HashSet

    public UsuarioService() {
        this.connection = new Conexao(); // cria a conexão
    }

    public Set<Usuario> listarUsuarios() {
        Connection conn = connection.getConexao(); // acessa o banco de dados e armazena na variavel conn
        return new UsuarioDAO(conn).listar();
    }

    public Usuario buscarUsuarioPorLogin(String login) {
        Connection conn = connection.getConexao();
        Usuario usuario = new UsuarioDAO(conn).listarPorLogin(login);
        if(usuario != null) {
            return usuario;
        } else {
            throw new RegraDeNegocioException("Não existe conta cadastrada com esse número!!!");
        }
    }

    public void abrirUsuario (DadosAberturaLogin dadosUsuario) {
        Connection conn = connection.getConexao();
        new UsuarioDAO(conn).salvar(dadosUsuario);
    }

    public void alterarUsuario(String login, String senha) {
        Connection conn = connection.getConexao();
        new UsuarioDAO(conn).alterar(login, senha);
    }

    public void encerrar(String login) {
        var usuario = buscarUsuarioPorLogin(login);
        Connection conn = connection.getConexao();
        new UsuarioDAO(conn).encerrar(login);
    }
}
