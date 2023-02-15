class EditorVideo extends Funcionario{
	
	public double getBonification() {
		System.out.println("Chamando Bonificação Editor de Video");
		return getBonification() + 100; // super --> Indica que esse atributo é de uma classe mãe
	}
}
