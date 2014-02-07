package br.com.flexait.gateway.enums;

public enum EFormaPagamento {
	
	CreditoAVista(1),
	ParceladoLoja(2), 
	ParceladoAdministradora(3), 
	Debito('A');
	
	private int valor;
	
	EFormaPagamento(int valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return String.valueOf(valor);
	}
}
