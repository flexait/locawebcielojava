package br.com.flexait.gateway.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.flexait.gateway.enums.EAmbiente;
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
	
	@Test(expected = GatewayException.class)
	public void deveDarErroSeArquivoInvalidor() throws Exception {
		
		PropertiesUtil.of("/asdf");
		
	}
	
}
