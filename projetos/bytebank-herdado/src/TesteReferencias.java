
public class TesteReferencias {
	public static void main(String[] args) {
		Gerente g1 = new Gerente();
	//  referencia variavel = new tipoDeObjeto(parametro);
		
		g1.setNome("Marcos");
		g1.setSalario(5000.0);
		
		EditorVideo ev1 = new EditorVideo();
		ev1.setSalario(2500.0);
		
		Desingner d1 = new Desingner();
		d1.setSalario(3500.0);
		
		ControleBonification controle = new ControleBonification();
		controle.registra(g1);
		controle.registra(ev1);
		controle.registra(d1);
		
		
		System.out.println(controle.getSoma());
		
	}

}
