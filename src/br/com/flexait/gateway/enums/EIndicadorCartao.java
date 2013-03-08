package br.com.flexait.gateway.enums;

public enum EIndicadorCartao {
	
	NaoInformado(0),
	Informado(1),
	Ilegivel(2),
	Inexistente(9);
	
	private int valor;
	
	private EIndicadorCartao(int valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return String.valueOf(valor);
	}
}
