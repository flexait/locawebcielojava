package br.com.flexait.gateway.xml;

import br.com.flexait.gateway.util.NumberUtil;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DoubleConverter implements Converter {

	public static DoubleConverter getInstance() {
		return new DoubleConverter();
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
		writer.setValue(NumberUtil.format((double)value));
	}

	public Object unmarshal(HierarchicalStreamReader reader,
            UnmarshallingContext context) {
		return NumberUtil.parse(reader.getValue());
	}
	
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class clazz) {
		return Double.class.isAssignableFrom(clazz);
	}
	
}
