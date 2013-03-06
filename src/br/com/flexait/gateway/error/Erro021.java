package br.com.flexait.gateway.error;

public class Erro021 extends AErro {

	public String getCodigo() {
		return "021";
	}

	public String getMensagem() {
		return "Prazo de autorização vencido";
	}

	public String getDescricao() {
		return "Não é permitido realizar autorização, pois o prazo está vencido";
	}

}
