
public class SistemaInterno {
	
	private int senha = 2222; // Senha válida
	
	public void autentica(Autenvicavel fa) { // método que vai chamar A classe intermediaria
		
		boolean autenticou = fa.autentica(this.senha); // variavel booleana que vai checar se a senha inserida é a valida, se sim tue, se não false
		if(autenticou) {
			System.out.println("Pode entrar no sistema"); 
		} else {
			System.out.println("Não pode entrar no sistema");
		}
		
	}

}
