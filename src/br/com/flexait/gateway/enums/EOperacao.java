package br.com.flexait.gateway.enums;

public enum EOperacao {
	
	Registro("Registro"), 
	Captura("Captura"), 
	Consulta("Consulta"), 
	Autorizacao("Autorizacao"),
	AutorizacaoDireta("Autorizacao-Direta");
	
	private String valor;
	
	private EOperacao(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return valor;
	}
	
	@Override
	public String toString() {
		return valor;
	}
	
}
