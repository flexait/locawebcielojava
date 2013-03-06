package br.com.flexait.gateway.error;

public class Erro041 extends AErro {

	public String getCodigo() {
		return "041";
	}

	public String getMensagem() {
		return "Status não permite cancelamento";
	}

	public String getDescricao() {
		return "O atual status da transação não permite cancelamento";
	}

}
