package br.com.mailtoDesenvolper.myBank.model.usuario;

public class Usuario {
	
	private String nome;
	private String numero;
	
	public Usuario(DadosCadastroUsuario dados) {
		this.nome = dados.nome();
		this.numero = dados.numero();
	}


	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", numero=" + numero + "]";
	}

	public String getNome() {
		return nome;
	}

	public String getNumero() {
		return numero;
	}
	
	
}
