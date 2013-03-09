package br.com.flexait.gateway.mock;

import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.interfaces.IGatewayService;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.service.ParametrosValidate;
import br.com.flexait.gateway.xml.ParserTest;

public class GatewayMock implements IGatewayService {
	
	protected static Retorno OK() throws GatewayException, Exception {
		return ParserTest.getRetornoTransacao();
	}
	
	protected static Retorno ERRO() throws GatewayException, Exception {
		return ParserTest.getRetornoErro();
	}
	
	protected Retorno check(boolean valid) throws Exception {
		if(valid) {
			return OK();
		}
		return ERRO();
	}

	@Override
	public Retorno autorizacaoDireta(Parametros params) throws Exception {
		return check(ParametrosValidate.of(params).validateAutorizacaoGroup());
	}

	@Override
	public Retorno consultar(Parametros params) throws Exception {
		return check(ParametrosValidate.of(params).validateTidGroup());
	}

	@Override
	public Retorno capturar(Parametros params) throws Exception {
		return check(ParametrosValidate.of(params).validateTidGroup());
	}

	@Override
	public Retorno cancelar(Parametros params) throws Exception {
		return check(ParametrosValidate.of(params).validateTidGroup());
	}

	public static GatewayMock of() {
		return new GatewayMock();
	}
	
}
