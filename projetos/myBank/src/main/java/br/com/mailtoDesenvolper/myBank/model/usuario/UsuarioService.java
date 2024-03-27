package br.com.mailtoDesenvolper.myBank.model.usuario;

import java.sql.Connection;

import br.com.mailtoDesenvolper.myBank.model.DAO.Conexao;
import br.com.mailtoDesenvolper.myBank.model.DAO.UsuarioDAO;

public class UsuarioService {
	private Conexao conexao;
	
	public UsuarioService() {
		this.conexao = new Conexao();
	}
	
	public void salvarDados(DadosCadastroUsuario dados) {
		Connection conn = conexao.getConexao();
		new UsuarioDAO(conn).salvar(dados);
	}
}
