package br.com.flexait.gateway.util;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class DateUtilTest {

	@Test
	public void deveRetornarDataFormataAnoMes() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2000);
		calendar.set(Calendar.MONTH, 0);
		
		int value = DateUtil.getDataAnoMes(calendar);
		assertEquals("deve ser igual a 200001", 200001, value);
	}

}
