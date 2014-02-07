package br.com.flexait.gateway.xml;

import java.util.Calendar;

import br.com.flexait.gateway.util.DateUtil;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CalendarConverter implements Converter {

	public static CalendarConverter getInstance() {
		return new CalendarConverter();
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
		writer.setValue(DateUtil.format(value));
	}

	public Object unmarshal(HierarchicalStreamReader reader,
            UnmarshallingContext context) {
		return DateUtil.parse(reader);
	}
	
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class clazz) {
		return Calendar.class.isAssignableFrom(clazz);
	}
	
}
