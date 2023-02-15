//Gerente é um FuncionarioAutenticavel e um Autenticavel / Gerente herda classe FuncionarioAutenticavel e assina um autenticavel
class Gerente extends Funcionario implements Autenvicavel{
	
	private AutenticadorUtil autenticador;

	@Override
	public void setSenha(int senha) {
		this.autenticador.setSenha(senha);
		
	}

	@Override
	public boolean autentica(int senha) {
		return this.autenticador.autentica(senha);
	}
	
	public double getBonification() {
		System.out.println("Chamando Bonificação Gerente");
		return getBonification() + super.getSalario(); // super --> Indica que esse atributo é de uma classe mãe
	}
}
