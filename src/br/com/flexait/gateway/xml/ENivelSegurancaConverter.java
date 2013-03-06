package br.com.flexait.gateway.xml;

import br.com.flexait.gateway.enums.ENivelSeguranca;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.enums.EnumConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ENivelSegurancaConverter implements Converter {

	public static ENivelSegurancaConverter getInstance() {
		return new ENivelSegurancaConverter();
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
		context.convertAnother(value, new EnumConverter());
	}

	public Object unmarshal(HierarchicalStreamReader reader,
            UnmarshallingContext context) {
		return ENivelSeguranca.get(reader.getValue());
	}
	
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class clazz) {
		return ENivelSeguranca.class.isAssignableFrom(clazz);
	}
	
}
