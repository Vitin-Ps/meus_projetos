
public class TesteFuncionario {
	public static void main(String[] args) {
		
		Funcionario nico = new Gerente();
		
		nico.setNome("Nico Stepoot");
		nico.setCpf("222.222.222-22");
		nico.setSalario(2600.00);
		
		System.out.println(nico.getNome());
		System.out.println(nico.getBonification());
	}

}
