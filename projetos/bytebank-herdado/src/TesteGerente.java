
public class TesteGerente {
	public static void main(String[] args) {
		Gerente vitin = new Gerente();
		vitin.setNome("Vitin");
		vitin.setCpf("333.333.333-33");
		vitin.setSalario(5000.0);
		System.out.println(vitin.getNome());
		System.out.println(vitin.getCpf());
		System.out.println(vitin.getSalario());
		
		boolean autenticou = vitin.autentica(1237);
		System.out.println(autenticou);
		
		
		System.out.println(vitin.getBonification());
	}

}
