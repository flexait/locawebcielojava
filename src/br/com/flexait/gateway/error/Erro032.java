package br.com.flexait.gateway.error;

public class Erro032 extends AErro {

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
