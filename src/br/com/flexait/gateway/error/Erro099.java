package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro099 implements IErro {

	public String getCodigo() {
		return "099";
	}

	public String getMensagem() {
		return "Erro inesperado";
	}

	public String getDescricao() {
		return "Falha no sistema";
	}

}
