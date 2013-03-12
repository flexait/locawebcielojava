package br.com.flexait.gateway.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.exception.GatewayException;

public class PropertiesUtilTest {

	
	@Test
	public void deveRetornarAmbiente() throws Exception {
		
		EAmbiente ambiente = PropertiesUtil.of(PropertiesUtil.GATEWAY_FILE).getAmbiente();
		assertEquals("Deve retornar ambiente de teste", EAmbiente.TESTE, ambiente);
		
	}
	
	@Test
	public void deveRetornarChaveIdentificacao() throws Exception {
		
		String key = PropertiesUtil.of().getIdentificador();
		assertEquals("Valor deve ser 1006993069", "1006993069", key);
		
	}
	
	@Test
	public void deveRetornarUrl() throws Exception {
		
		String key = PropertiesUtil.of().getUrl();
		assertEquals("Valor deve ser https://comercio.locaweb.com.br/comercio.comp", "https://comercio.locaweb.com.br/comercio.comp", key);
		
	}
	
	@Test
	public void deveRetornarModulo() throws Exception {
		
		EModulo modulo = PropertiesUtil.of().getModulo();
		assertEquals("Valor deve ser CIELO", EModulo.CIELO, modulo);
		
	}
	
	@Test
	public void deveRetornarEncode() throws Exception {
		String encode = PropertiesUtil.of().getEncode();
		assertEquals("Valor deve ser CIELO", "ISO-8859-1", encode);
	}
	
	@Test(expected = GatewayException.class)
	public void deveDarErroSeArquivoInvalidor() throws Exception {
		
		PropertiesUtil.of("/asdf");
		
	}
	
}
