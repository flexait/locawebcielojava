package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro032 implements IErro {

	public String getCodigo() {
		return "032";
	}

	public String getMensagem() {
		return "Valor de captura inválido";
	}

	public String getDescricao() {
		return "O valor solicitado para captura não é válido";
	}

}
