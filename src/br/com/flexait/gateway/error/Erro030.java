package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro030 implements IErro {

	public String getCodigo() {
		return "030";
	}

	public String getMensagem() {
		return "Transação não autorizada para captura";
	}

	public String getDescricao() {
		return "A captura não pode ser realizada, pois a transação não está autorizada";
	}

}
