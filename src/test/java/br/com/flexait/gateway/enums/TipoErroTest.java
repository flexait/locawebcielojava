package br.com.flexait.gateway.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TipoErroTest {

	@Test
	public void deveRetornarNenhumComParametroNullOuVazio() {
		ETipoErro tipoErro = ETipoErro.get("");
		assertEquals("Deve retornar NENHUM", ETipoErro.NENHUM, tipoErro);
	}
	
	@Test
	public void deveRetornarNenhumComParametroNull() {
		ETipoErro tipoErro = ETipoErro.get(null);
		assertEquals("Deve retornar NENHUM", ETipoErro.NENHUM, tipoErro);
	}
	
	@Test
	public void deveRetornar001() {
		ETipoErro tipoErro = ETipoErro.get("001");
		assertEquals("Deve retornar E001", ETipoErro.E001, tipoErro);
	}
}
