package br.com.flexait.gateway.xml;

import br.com.flexait.gateway.enums.EStatus;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.enums.EnumConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class EStatusConverter implements Converter {

	public static EStatusConverter getInstance() {
		return new EStatusConverter();
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
		context.convertAnother(value, new EnumConverter());
	}

	public Object unmarshal(HierarchicalStreamReader reader,
            UnmarshallingContext context) {
		return EStatus.get(reader.getValue());
	}
	
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class clazz) {
		return EStatus.class.isAssignableFrom(clazz);
	}
	
}
