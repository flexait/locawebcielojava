package br.com.flexait.gateway.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.flexait.gateway.enums.EIdentificadorCartao;
import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.integracao.IntegracaoTest;
import br.com.flexait.gateway.model.Parametros;

public class ParametrosValidateTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = GatewayException.class)
	public void deveInvalidarParametrosRegistro() throws GatewayException {
		Parametros parametros = IntegracaoTest.getParametrosRegistro();
		parametros.setBandeira(null);
		ParametrosValidate.of(parametros).validate();
	}
	
	@Test(expected = GatewayException.class)
	public void deveInvalidarParametrosConsulta() throws GatewayException {
		Parametros parametros = Parametros.of(EOperacao.Consulta, "");
		ParametrosValidate.of(parametros).validate();
	}
	
	@Test(expected = GatewayException.class) 
	public void deveInvalidarParametrosCaptura() throws GatewayException {		
		Parametros parametros =  Parametros.of(EOperacao.Captura, "");
		ParametrosValidate.of(parametros).validate();
	}
	
	@Test(expected = GatewayException.class) 
	public void deveInvalidarParametrosCancelamento() throws GatewayException {		
		Parametros parametros =  Parametros.of(EOperacao.Cancelamento, "");
		ParametrosValidate.of(parametros).validate();
	}
	
	@Test public void deveValidarDadosCartao() {
		Parametros parametros = Parametros.of(EOperacao.AutorizacaoDireta, null);
		parametros.setNomePortadorCartao("d");
		parametros.setNumeroCartao("4234567896541234");
		parametros.setCodigoSegurancaCartao("123");
		parametros.setValidadeCartao("200010");
		parametros.setIndicadorCartao(EIdentificadorCartao.Informado);
		assertTrue("deve retornar null com numero cartão invalido", ParametrosValidate.of(parametros).dadosCartaoValidate());
		
		parametros.setNomePortadorCartao(null);
		assertFalse("deve retornar null com numero cartão invalido", ParametrosValidate.of(parametros).dadosCartaoValidate());
		
		parametros.setNomePortadorCartao("asdf");
		parametros.setNumeroCartao("");
		assertFalse("deve retornar null com numero cartão invalido", ParametrosValidate.of(parametros).dadosCartaoValidate());
		parametros.setNumeroCartao("2234567896541234");
		assertFalse("deve retornar null com numero cartão invalido", ParametrosValidate.of(parametros).dadosCartaoValidate());
		parametros.setNumeroCartao("423456789654124");
		assertFalse("deve retornar null com numero cartão invalido", ParametrosValidate.of(parametros).dadosCartaoValidate());
		
		parametros.setNumeroCartao("4234567896541234");
		parametros.setValidadeCartao("");
		assertFalse("deve retornar null com numero cartão invalido", ParametrosValidate.of(parametros).dadosCartaoValidate());
		parametros.setValidadeCartao("2010");
		assertFalse("deve retornar null com numero cartão invalido", ParametrosValidate.of(parametros).dadosCartaoValidate());
		
		parametros.setValidadeCartao("200010");
		parametros.setCodigoSegurancaCartao("");
		assertFalse("deve retornar null com numero cartão invalido", ParametrosValidate.of(parametros).dadosCartaoValidate());
		parametros.setCodigoSegurancaCartao("1");
		assertFalse("deve retornar null com numero cartão invalido", ParametrosValidate.of(parametros).dadosCartaoValidate());
		parametros.setIndicadorCartao(EIdentificadorCartao.Ilegivel);
		assertTrue("deve retornar null com numero cartão invalido", ParametrosValidate.of(parametros).dadosCartaoValidate());		
	}
	
	@Test public void deveValidarDadosDaTransacao() {
		
	}

}
