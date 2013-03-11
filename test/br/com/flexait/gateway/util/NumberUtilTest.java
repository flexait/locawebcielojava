package br.com.flexait.gateway.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.flexait.gateway.util.NumberUtil;

public class NumberUtilTest {

	@Test
	public void deveRetornarDoubleFormatado() {
		String format = NumberUtil.format(100.01);
		assertEquals("Deve formatar numero", "10001", format);
		
		format = NumberUtil.format(100.0);
		assertEquals("Deve formatar numero", "10000", format);
		
		format = NumberUtil.format(0.01);
		assertEquals("Deve formatar numero", "1", format);
	}
	
	@Test
	public void deveRetornarDouble() {
		double parse = NumberUtil.parse("100");
		assertEquals("Deve formatar numero", 1.0, parse, 0.01);
		
		parse = NumberUtil.parse("1");
		assertEquals("Deve formatar numero", 0.01, parse, 0.01);
		
		parse = NumberUtil.parse("1001");
		assertEquals("Deve formatar numero", 10.01, parse, 0.01);
	}

}
