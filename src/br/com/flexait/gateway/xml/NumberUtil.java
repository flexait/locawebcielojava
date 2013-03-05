package br.com.flexait.gateway.xml;

import java.text.DecimalFormat;

public class NumberUtil {

	public static String format(double value) {
		DecimalFormat df = new DecimalFormat("#.00");
		
		String format = df.format(value);
		return format.replace(".", "");
	}

}
