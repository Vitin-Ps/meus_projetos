
//Interfaces não podem ter nada concreto nem implementações

//Contrato --> Autenticavel
	// Quem assina esse contrato precisa iplementar
		// método autentica

public abstract interface Autenvicavel{
	
	public abstract void setSenha(int senha);

	public abstract boolean autentica(int senha);

}
