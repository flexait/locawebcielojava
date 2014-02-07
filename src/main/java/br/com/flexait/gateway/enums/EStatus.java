package br.com.flexait.gateway.enums;

import br.com.flexait.gateway.xml.EStatusConverter;

import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamConverter(EStatusConverter.class)
public enum EStatus {
	
	Criada(0),
	EmAndamento(1),
	Autenticada(2),
	NaoAutenticada(3),
	AutorizadaOuPendenteDeCaptura(4),
	NaoAutorizada(5),
	Capturada(6),
	NaoCapturada(8),
	Cancelada(9),
	EmAutenticacao(10),
	None(-1);
	
	private int codigo;
	
	EStatus(String codigo) {
		this.codigo = Integer.parseInt(codigo);
	}
	
	EStatus(int codigo) {
		this.codigo = codigo;
	}
	
	public static EStatus get(String value) {
		for (EStatus e: EStatus.values()) {
			if(e.getCodigo() == Integer.parseInt(value)) {
				return e;
			}
		}
		return None;
	}
	
	public int getCodigo() {
		return codigo;
	}
}
