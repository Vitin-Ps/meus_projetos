
public class ControleBonification {
	
	private double soma;
	
	public void registra(Funcionario f) {// Aqui está sendo criado um método que aborda a classe mãe, por tanto serve pra todas as filhas
		double boni = f.getBonification();
		this.setSoma(this.getSoma() + boni);
	}

	public double getSoma() {
		return soma;
	}

	public void setSoma(double soma) {
		this.soma = soma;
	}

}
