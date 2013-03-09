package br.com.flexait.gateway.mock;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.service.GatewayServiceTest;

public class GatewayMockTest {
	
	GatewayMock mock;
	Parametros params;
	
	@Before
	public void setUp() throws Exception {
		mock = GatewayMock.of();
		params = GatewayServiceTest.getParametrosRegistro();
	}
	
	@After
	public void tearDown() {
		mock = null;
		params = null;
	}
	
	@Test
	public void deveRetornarOk() throws Exception {
		assertNotNull("deve retornar ok", mock.check(true).getTransacao());
	}
	
	@Test
	public void deveRetornarErro() throws Exception {
		assertNotNull("deve retornar ok", mock.check(false).getErro());
	}
	
	@Test
	public void deveRetornarOKAutorizacao() throws Exception {
		assertNotNull("deve retornar ok", mock.autorizacaoDireta(params).getTransacao());
	}
	
	@Test
	public void deveRetornarErroConsulta() throws Exception {
		params.setTid(null);
		assertNotNull("deve retornar ok", mock.consultar(params).getErro());
	}

}
