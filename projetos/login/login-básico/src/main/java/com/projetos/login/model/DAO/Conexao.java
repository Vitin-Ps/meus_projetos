package com.projetos.login.model.DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Conexao {

    private HikariDataSource criarDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/projeto_login");
        config.setUsername("root");
        config.setPassword("");
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }

    public Connection getConexao() {
        try {
            return criarDataSource().getConnection();
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
