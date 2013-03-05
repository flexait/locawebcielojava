package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro021 implements IErro {

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
