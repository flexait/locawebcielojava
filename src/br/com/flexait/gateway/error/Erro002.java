package br.com.flexait.gateway.error;

public class Erro002 extends AErro {

	public String getCodigo() {
		return "002";
	}

	public String getMensagem() {
		return "Credenciais inválidas";
	}

	public String getDescricao() {
		return "Impossibilidade de autenticar uma requisição da loja virtual";
	}

}
