
public class ContaCorrente extends Conta implements Tributavel{
	
	//classes filhas não herdam contrutores da classe mãe automaticamente
	
	public ContaCorrente(int agencia, int numero) {
		super(agencia, numero); // Chamando Construtor especifico da classe mãe 
	}
	
	@Override
	public boolean saca(double valor) {
		double ValorASacar = valor + 0.2;
		return super.saca(ValorASacar);
	}

	@Override
	public void deposita(double valor) {
		super.saldo += valor;
		
	}

	@Override
	public double getValorImposto() {
		return super.saldo * 0.01;
	}

}

