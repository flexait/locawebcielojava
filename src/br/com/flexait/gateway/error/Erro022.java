package br.com.flexait.gateway.error;

public class Erro022 extends AErro {

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
