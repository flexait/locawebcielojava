package br.com.flexait.gateway.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.service.GatewayServiceTest;

public class PropertiesUtilTest {

	private static final String IDENTIFICACAO = GatewayServiceTest.IDENTIFICACAO;

	PropertiesUtil util;
	
	@Before
	public void setUp() throws Exception {
		util = spy(PropertiesUtil.of());
	}
	
	@After
	public void tearDown() {
		util = null;
	}
	
	@Test
	public void deveRetornarAmbiente() throws Exception {
		when(util.get(Mockito.anyString())).thenReturn("TESTE");
		
		EAmbiente ambiente = PropertiesUtil.of(PropertiesUtil.GATEWAY_FILE).getAmbiente();
		assertEquals("Deve retornar ambiente de teste", EAmbiente.TESTE, ambiente);
	}
	
	@Test
	public void deveRetornarChaveIdentificacao() throws Exception {
		when(util.get(Mockito.anyString())).thenReturn(IDENTIFICACAO);
		
		String key = util.getIdentificador();
		assertEquals("Valor deve ser " + IDENTIFICACAO, IDENTIFICACAO, key);
	}
	
	@Test
	public void deveRetornarUrl() throws Exception {
		when(util.get(Mockito.anyString())).thenReturn("url");
		
		String key = util.getUrl();
		assertEquals("Valor deve ser url", "url", key);
	}
	
	@Test
	public void deveRetornarModulo() throws Exception {
		when(util.get(Mockito.anyString())).thenReturn("CIELO");
		
		EModulo modulo = util.getModulo();
		assertEquals("Valor deve ser CIELO", EModulo.CIELO, modulo);
		
	}
	
	@Test
	public void deveRetornarEncode() throws Exception {
		String enc = "ISO-8859-1";
		when(util.get(Mockito.anyString())).thenReturn(enc);
		
		String encode = util.getEncode();
		
		assertEquals("Valor deve ser " + enc, enc, encode);
	}
	
	@Test(expected = GatewayException.class)
	public void deveDarErroSeArquivoInvalidor() throws Exception {
		PropertiesUtil.of("/asdf");
	}
	
}
