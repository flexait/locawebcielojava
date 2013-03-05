package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro041 implements IErro {

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
