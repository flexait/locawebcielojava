package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro002 implements IErro {

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
