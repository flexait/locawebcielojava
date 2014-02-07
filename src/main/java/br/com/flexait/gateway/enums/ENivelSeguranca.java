package br.com.flexait.gateway.enums;

import br.com.flexait.gateway.xml.ENivelSegurancaConverter;

import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamConverter(ENivelSegurancaConverter.class)
public enum ENivelSeguranca {
	
	VisaPortadorAutenticado(5),
	VisaPortadorRealizouAutenticacao(6),
	VisaPortadorNaoSeAutenticou(7),
	
	MasterCardPortadorAutenticado(2),
	MasterCardPortadorRealizouAutenticacao(1),
	MasterCardPortadorNaoSeAutenticou(0),
	
	None(-1);
	
	private int valor;

	private ENivelSeguranca(int valor) {
		this.valor = valor;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static ENivelSeguranca get(String value) {
		for (ENivelSeguranca e: ENivelSeguranca.values()) {
			if(e.getValor() == Integer.parseInt(value)) {
				return e;
			}
		}
		return None;
	}
}
