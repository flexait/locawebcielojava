package br.com.flexait.gateway.error;

public class Erro031 extends AErro {

	public String getCodigo() {
		return "031";
	}

	public String getMensagem() {
		return "Prazo de captura vencido";
	}

	public String getDescricao() {
		return "A captura não pode ser realizada, pois o prazo para captura está vencido";
	}

}
