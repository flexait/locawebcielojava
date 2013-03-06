package br.com.flexait.gateway.error;

public class Erro033 extends AErro {

	public String getCodigo() {
		return "033";
	}

	public String getMensagem() {
		return "Falha ao capturar";
	}

	public String getDescricao() {
		return "Não foi possível realizar a captura";
	}

}
