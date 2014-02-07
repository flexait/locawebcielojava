package br.com.flexait.gateway.error;


public class Erro001 extends AErro {

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
