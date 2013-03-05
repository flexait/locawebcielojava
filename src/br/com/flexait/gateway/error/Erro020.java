package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro020 implements IErro {

	public String getCodigo() {
		return "020";
	}

	public String getMensagem() {
		return "Status não permite autorização";
	}

	public String getDescricao() {
		return "Não é permitido realizar autorização para o status da transação";
	}

}
