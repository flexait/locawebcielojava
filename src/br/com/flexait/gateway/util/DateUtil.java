package br.com.flexait.gateway.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;

public class DateUtil {

	private static final String DATE_TIME_MASK = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	public static String format(Object value) {
		Calendar calendar = (Calendar) value;
		Date date = calendar.getTime();
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL,
				getLocale());
		return formatter.format(date);
	}

	public static Calendar parse(HierarchicalStreamReader reader) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_MASK);
			
			String value = reader.getValue();
			Date parse = sdf.parse(value.replaceFirst("(\\-\\d{2}):(\\d{2})", "$1$2"));
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parse);
			
			return calendar;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Locale getLocale() {
		return new Locale("pt-br");
	}

}
