package br.com.flexait.gateway.error;

public class Erro030 extends AErro {

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
