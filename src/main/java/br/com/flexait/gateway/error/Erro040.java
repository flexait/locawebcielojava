package br.com.flexait.gateway.error;

public class Erro040 extends AErro {

	public String getCodigo() {
		return "040";
	}

	public String getMensagem() {
		return "Prazo de cancelamento vencido";
	}

	public String getDescricao() {
		return "O cancelamento não pode ser realizado, pois o prazo está vencido";
	}

}
