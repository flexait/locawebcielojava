package br.com.flexait.gateway.mock;

import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.interfaces.IGatewayService;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.service.ParametrosValidate;
import br.com.flexait.gateway.util.PropertiesUtil;
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
		params.setOperacao(EOperacao.AutorizacaoDireta);
		return check(getPropertiesAndValidateTransacao(params));
	}

	@Override
	public Retorno consultar(Parametros params) throws Exception {
		params.setOperacao(EOperacao.Consulta);
		return check(getPropertiesAndValidateTid(params));
	}

	@Override
	public Retorno capturar(Parametros params) throws Exception {
		params.setOperacao(EOperacao.Captura);
		return check(getPropertiesAndValidateTid(params));
	}

	@Override
	public Retorno cancelar(Parametros params) throws Exception {
		params.setOperacao(EOperacao.Cancelamento);
		return check(getPropertiesAndValidateTid(params));
	}
	
	private Parametros getPropertiesParameters(Parametros params) throws Exception {
		PropertiesUtil util = PropertiesUtil.of();
		params.setAmbiente(util.getAmbiente());
		params.setIdentificacao(util.getIdentificador());
		params.setModulo(util.getModulo());
		return params;
	}
	
	private boolean getPropertiesAndValidateTransacao(Parametros params) throws Exception {
		params = getPropertiesParameters(params);
		return ParametrosValidate.of(params).validateAutorizacaoGroup();
	}

	private boolean getPropertiesAndValidateTid(Parametros params) throws Exception {
		params = getPropertiesParameters(params);
		return ParametrosValidate.of(params).validateTidGroup();
	}

	public static GatewayMock of() {
		return new GatewayMock();
	}
	
}
