package com.projetos.login.model.DAO;

import com.projetos.login.model.usuario.DadosAberturaLogin;
import com.projetos.login.model.usuario.Usuario;
import com.projetos.login.model.usuario.UsuarioRole;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO(Connection connection) {
        this.conn = connection;
    }

    public void salvar(DadosAberturaLogin dadosUsuario) {
        var usuario = new Usuario(dadosUsuario);

        String sql = "INSERT INTO usuario (login, senha, tipo_usuario) VALUES (?, ?, ?);";
        // esse é a query que vai adicionar a tabela

        try { // como a conexão é um CHECKED, é preciso de um THROWS (Nome da exceção) no inicio da classe ou, criar um try cacth
            PreparedStatement preparedStatement = conn.prepareStatement(sql); // variavel criada para tratar o código sql

            // como é um codigo de inserir dados, aqui vamos captar os dados sql
            preparedStatement.setString(1, usuario.getLogin());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setString(1, usuario.getTipoUsuario());

            preparedStatement.execute(); // esse código Java executa o código Sql no mysql
            preparedStatement.close(); // fecha o preparent
            conn.close(); // fecha a conexão
        } catch (SQLException ex) {
            throw new RuntimeException(ex); // trata a exceção
        }
    }

    public Set<Usuario> listar() { // aqui o uso so set em vez do list serve
        // para que as informações não sejam duplicadas, ja que o set apresenta os itens num unico elemento com posição aleatória
        Set<Usuario> usuarios = new HashSet<>();

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        String sql = "SELECT * FROM usuario";
        try {
            preparedStatement = conn.prepareStatement(sql); // criando o ps com a conexão e o código sql
            resultSet = preparedStatement.executeQuery(); // // diferente do execute, ele retorna um result set

            while (resultSet.next()) { // enquanto tiver informação no resultset
                String login = resultSet.getString(1);
                String senha = resultSet.getString(2);
                String tipoUsuario = resultSet.getString(3);

                DadosAberturaLogin dadosUsuario = new DadosAberturaLogin(login, senha, tipoUsuario); //criando um novo objeto record e recebendo os parametros pegos do mysql
                Usuario usuario = new Usuario(dadosUsuario); // criando usuario com os dados do record
                usuarios.add(new Usuario(dadosUsuario)); // criando uma novo Usuario se jogando ele no set<>
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return usuarios; // retorna a lista Set<Conta>
    }

    public void alterar(DadosAberturaLogin daods) {
        PreparedStatement preparedStatement;
        String sql = "UPDATE usuario SET login = ?, senha = ?, tipo_usuario = ? WHERE login = ?";

        try {
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, daods.login());
            preparedStatement.setString(2, daods.senha());
            preparedStatement.setString(3, daods.tipoUsuario());
            preparedStatement.setString(4, daods.login());



            preparedStatement.execute(); // esse código Java executa o código Sql no mysql
            preparedStatement.close(); // fecha o preparent
            conn.close(); // fecha a conexão
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void encerrar(String login) {
        String sql = "DELETE FROM usuario WHERE login = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, login);

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Usuario listarPorLogin(String loginSelecionado) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sql = "SELECT * FROM usuario WHERE login = ?";
        Usuario usuario = null;

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, loginSelecionado);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String login = resultSet.getNString(1);
                String senha = resultSet.getNString(2);
                UsuarioRole tipoUsuario = resultSet.getEn(3);

                DadosAberturaLogin dadosUsuario = new DadosAberturaLogin(login, senha, tipoUsuario);
                usuario = new Usuario(dadosUsuario);
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return usuario;
    }
}
