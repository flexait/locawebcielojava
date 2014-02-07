package br.com.flexait.gateway.error;

public class Erro020 extends AErro {

	public String getCodigo() {
		return "020";
	}

	public String getMensagem() {
		return "Status não permite autorização";
	}

	public String getDescricao() {
		return "Não é permitido realizar autorização para o status da transação";
	}

}
