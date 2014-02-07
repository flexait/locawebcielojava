package br.com.flexait.gateway.model;

import br.com.flexait.gateway.error.AErro;

public class ErroInv extends AErro {

	@Override
	public String getCodigo() {
		return "invalido";
	}

	@Override
	public String getMensagem() {
		return "XML não pode ser parseado";
	}

	@Override
	public String getDescricao() {
		return "XML não pode ser parseado";
	}

}
