package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro010 implements IErro {

	public String getCodigo() {
		return "010";
	}

	public String getMensagem() {
		return "Inconsistência no envio do cartão";
	}

	public String getDescricao() {
		return "A transação, com ou sem cartão, está divergente com a permissão do envio dessa informação";
	}

}
