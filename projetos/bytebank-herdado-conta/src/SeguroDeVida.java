
public class SeguroDeVida implements Tributavel {
	
	private double saldo;
	
	public double CalculoSeguroDeVida(ContaCorrente c) {
		return this.saldo = c.getSaldo() * 10;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	@Override
	public double getValorImposto() {
		return this.saldo * 0.01;
	}

}
