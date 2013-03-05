package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro042 implements IErro {

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
