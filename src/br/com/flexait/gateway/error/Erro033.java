package br.com.flexait.gateway.error;

import br.com.flexait.gateway.interfaces.IErro;

public class Erro033 implements IErro {

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
