package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro001 implements IErro {

	public String getCodigo() {
		return "001";
	}

	public String getMensagem() {
		return "Mensagem inválida";
	}

	public String getDescricao() {
		return "A mensagem XML está fora do formato especificado pelo arquivo ecommerce.xsd";
	}

}
