package br.com.flexait.gateway.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NumberUtilTest {

	@Test
	public void deveRetornarDoubleFormatado() {
		String format = NumberUtil.format(100.01);
		assertEquals("Deve formatar numero", "10001", format);
		
		format = NumberUtil.format(100.0);
		assertEquals("Deve formatar numero", "10000", format);
	}

}
