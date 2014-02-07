package br.com.flexait.gateway.error;

public class Erro042 extends AErro {

	public String getCodigo() {
		return "042";
	}

	public String getMensagem() {
		return "Falha ao cancelar";
	}

	public String getDescricao() {
		return "Não foi possível realizar o cancelamento";
	}

}
