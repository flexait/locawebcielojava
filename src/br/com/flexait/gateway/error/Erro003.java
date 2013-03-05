package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro003 implements IErro {

	public String getCodigo() {
		return "003";
	}

	public String getMensagem() {
		return "Transação inexistente";
	}

	public String getDescricao() {
		return "Não existe transação para o identificador informado";
	}

}
