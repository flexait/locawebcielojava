package br.com.flexait.gateway.enums;

import com.google.common.base.Strings;


public enum ETipoErro {
	
	E001, E002, E003, 
	E010, E011, E012, 
	E020, E021, E022, 
	E030, E031, E032, E033,
	E040, E041, E042,
	E099, E999,
	NENHUM, EINV;

	public static ETipoErro get(String erro) {
		if(Strings.isNullOrEmpty(erro)) {
			return NENHUM;
		}
		
		for(ETipoErro t: ETipoErro.values()) {
			if(t.toString().endsWith(erro)) {
				return t;
			}
		}
		return NENHUM;
	}
	
}
