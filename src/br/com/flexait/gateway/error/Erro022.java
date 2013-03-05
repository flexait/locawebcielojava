package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro022 implements IErro {

	public String getCodigo() {
		return "022";
	}

	public String getMensagem() {
		return "EC não autorizado";
	}

	public String getDescricao() {
		return "EC não possui permissão para realizar a autorização";
	}

}
