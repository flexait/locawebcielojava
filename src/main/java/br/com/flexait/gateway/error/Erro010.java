package br.com.flexait.gateway.error;

public class Erro010 extends AErro {

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
