package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro999 implements IErro {

	public String getCodigo() {
		return "999";
	}

	public String getMensagem() {
		return "Erro inesperado";
	}

	public String getDescricao() {
		return "Falha no sistema";
	}

}
