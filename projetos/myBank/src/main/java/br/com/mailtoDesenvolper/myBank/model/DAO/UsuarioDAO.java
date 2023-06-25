package br.com.mailtoDesenvolper.myBank.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.mailtoDesenvolper.myBank.model.usuario.DadosCadastroUsuario;
import br.com.mailtoDesenvolper.myBank.model.usuario.Usuario;

public class UsuarioDAO {
	
	private Connection conn;
	
	public UsuarioDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void salvar(DadosCadastroUsuario dadosUsuario) {
		var usuario = new Usuario(dadosUsuario);
		
		String sql = "INSERT INTO usuario (nome, numero) VALUES (?, ?)";
		
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, usuario.getNome());
			preparedStatement.setString(2, usuario.getNumero());
			
			preparedStatement.execute();
			preparedStatement.close();
			conn.close();
			
		} catch(SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

}
