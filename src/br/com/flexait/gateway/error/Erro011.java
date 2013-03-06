package br.com.flexait.gateway.error;

public class Erro011 extends AErro {

	public String getCodigo() {
		return "011";
	}

	public String getMensagem() {
		return "Modalidade não habilitada";
	}

	public String getDescricao() {
		return "A transação está configurada com uma modalidade de pagamento não habilitada para a loja";
	}

}
