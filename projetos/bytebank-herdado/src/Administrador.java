
public class Administrador extends Funcionario implements Autenvicavel{
	
	private AutenticadorUtil autenticador;
	
	public Administrador() {
		this.autenticador = new AutenticadorUtil(); 
	}

	@Override
	public void setSenha(int senha) {
		this.autenticador.setSenha(senha);
		
	}

	@Override
	public boolean autentica(int senha) {
		return this.autenticador.autentica(senha);
	}

	@Override
	public double getBonification() {
		// TODO Auto-generated method stub
		return 50;
	}


}
