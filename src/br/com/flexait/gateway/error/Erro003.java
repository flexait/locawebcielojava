package br.com.flexait.gateway.error;

public class Erro003 extends AErro {

	public String getCodigo() {
		return "003";
	}

	public String getMensagem() {
		return "Transação inexistente";
	}

	public String getDescricao() {
		return "Não existe transação para o identificador informado";
	}

}
