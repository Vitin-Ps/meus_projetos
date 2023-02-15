
public class TesteTributaveis {
	public static void main(String[] args) {
		ContaCorrente cc = new ContaCorrente(222, 333); 
		cc.deposita(1000);
	
		SeguroDeVida sv = new SeguroDeVida();
		sv.CalculoSeguroDeVida(cc);
		
		CalculadorImposto calc = new CalculadorImposto();
		calc.registra(cc);
		calc.registra(sv);
		
		System.out.println("O valor da conta cc é: R$" + cc.getSaldo());
		System.out.println("O valor do Seguro de Vida é: R$" + sv.getSaldo());
		System.out.println("O Total de Impostos dessa Conta é de: R$" + calc.getTotalImposto());
	}
}
