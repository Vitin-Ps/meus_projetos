public abstract class Conta {

	// Atributos
	protected double saldo;
	private int agencia;
	private int numero;
	private Cliente titular;
	private static int total;// o static informa que a variável é um atributo da classe e não do objeto
	private static double valorTotal;
	//---------------------------------------------
	
	// Construtores
	public Conta() {
		
	}
	
	public Conta(int agencia, int numero)/* Pode receber parametros */ { // é uma rotina de inicialização
		Conta.total ++;
		this.agencia = agencia;
		this.numero = numero;
		System.out.println("Estou criando uma conta!!!" + "\nAgencia: " + this.agencia + "\nNúmero: " + this.numero);
		System.out.println("O Total de Contas criadas é: " + Conta.total);	
	}
	//-------------------------------------------------
	
	/*--------------- Funcionalidades da conta ----------------*/

//	public  void deposita(double valor) {
//		this.saldo = this.saldo + valor;
//		Conta.valorTotal += this.saldo;
//	}
	
	public abstract void deposita(double valor);
	
	public boolean saca(double valor) {
		if (this.saldo >= valor) {
			this.saldo = this.saldo - valor;
			return true;
		} else {
			System.out.println("Valor Insuficiente na conta\nValor disponivel na conta: " + saldo);
			return false;
		}

	}

	public boolean transfere(double valor, Conta destino) {
		if (this.saca(valor)) {
			destino.deposita(valor);
			return true;
		} else {
			return false;
		}
	}
	//--------------------------------------------------
	
	/*----------------- Get e Set ----------------------*/

//	Sempre pergunte a si mesmo, é preciso de um get ou um set saldo?
	public double getSaldo() { // pra que fazer isso? para conseguir mostrar o saldo sem ter que acessa-lo
								// diretamente
		return this.saldo;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		if (numero <= 0) {
			System.out.println("Não pode valor menor ou igual a 0");
		}
		this.numero = numero; // este.atributo = variavel;
	}

	public int getAgencia() {
		return this.agencia;
	}

	public void setAgencia(int agencia) {
		if (agencia <= 0) {
			System.out.println("Não pode valor menor ou igual a 0");
			return; // como e void so precisa de retorno
		}
		this.agencia = agencia;
	}

	public void setTitular(Cliente titular) {
		this.titular = titular;
	}

	public Cliente getTitular() {
		return titular;
	}
	
	public static int getTotal() { // static está dizendo: este método é da classe em geral
		return total;
	}
	
	public static double getValorTotal() {
		return valorTotal;
	}
}

//---------------------------------------------------------