package br.com.mailtoDesenvolper.myBank.model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	
	public Connection getConexao() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cad_us", "root", "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return conn;
	}

}
