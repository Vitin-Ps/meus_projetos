
public class Cliente implements Autenvicavel{
	
	private AutenticadorUtil autenticador; // Criando uma váriavel da classe AutenticadorUtil

	@Override
	public void setSenha(int senha) {
		this.autenticador.setSenha(senha); // chamando método do AutenticadorUtil
		/*
		public void setSenha(int senha) {
		this.senha = senha;
	}
		  */
		
	}

	@Override
	public boolean autentica(int senha) {
		return this.autenticador.autentica(senha);
		/*
		public boolean autentica(int senha) {
		if(this.senha == senha) {
			return true;
		} else {
			return false;
		}
	} 
		*/
	}
}
