package br.com.flexait.gateway.enums;

public enum ENivelSeguranca {
	
	VisaPortadorAutenticado(5),
	VisaPortadorRealizouAutenticacao(6),
	VisaPortadorNaoSeAutenticou(7),
	
	MasterCardPortadorAutenticado(2),
	MasterCardPortadorRealizouAutenticacao(1),
	MasterCardPortadorNaoSeAutenticou(0);
	
	private int valor;

	private ENivelSeguranca(int valor) {
		this.valor = valor;
	}
	
	public int getValor() {
		return valor;
	}
}
