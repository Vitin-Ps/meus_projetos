package br.com.mailtoDesenvolper.myBank.model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	public Connection recuperarConexao() {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql//localhost:3306/cad_us";
		String user = "root";
		String pass = "";
		
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			return conn;
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
