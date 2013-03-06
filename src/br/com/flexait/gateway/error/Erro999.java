package br.com.flexait.gateway.error;

public class Erro999 extends AErro {

	public String getCodigo() {
		return "999";
	}

	public String getMensagem() {
		return "Erro inesperado";
	}

	public String getDescricao() {
		return "Falha no sistema";
	}

}