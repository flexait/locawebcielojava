package br.com.flexait.gateway.enums;

public enum EAutorizar {
	
	NaoAutorizar(0),
	AutorizarSomenteAutenticada(1),
	AutorizarAutenticadaENaoAutenticada(2),
	AutorizarSemPassarPorAutenticacao(3);
	
	private int valor;
	
	EAutorizar(int valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return String.valueOf(valor);
	}
	
}
