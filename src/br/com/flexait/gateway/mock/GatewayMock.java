package br.com.flexait.gateway.mock;

import com.google.common.base.Strings;

import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.interfaces.IGatewayService;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.xml.ParserTest;

public class GatewayMock implements IGatewayService {
	
	public static Retorno OK() throws Exception {
		return ParserTest.getRetornoTransacao();
	}

	public Retorno post(Parametros params) throws GatewayException {
		if(params == null) {
			throw new GatewayException("Parametros nulo");
		}
		
		EOperacao operacao = params.getOperacao();
		
		switch(operacao) {
		case Autorizacao:
		case AutorizacaoDireta:
		case Registro:
			
			break;
			
		case Cancelamento:
		case Captura:
		case Consulta:
			if(Strings.isNullOrEmpty(params.getTid())) {
				throw new GatewayException(String.format("Operação de %s tid obrigatório", operacao));
			}
			break;
		
		}
		
		return null;
	}
	
}
