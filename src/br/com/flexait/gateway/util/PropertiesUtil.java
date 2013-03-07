package br.com.flexait.gateway.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.Setter;

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.exception.GatewayException;

public class PropertiesUtil extends Properties {

	private static final long serialVersionUID = 4014546612069537902L;
	
	protected static final String GATEWAY_AMBIENTE = "gateway.ambiente";
	protected static final String GATEWAY_IDENTIFICADOR = "gateway.%s.identificador";

	protected static final String GATEWAY_FILE = "/gateway.properties";
	@Setter private EAmbiente ambiente;	
	
	PropertiesUtil() throws Exception {
		this(GATEWAY_FILE);
	}
	
	PropertiesUtil(String file) throws Exception  {
		super();
		try {
			InputStream inStream = getClass().getResourceAsStream(file);
			if(inStream == null) {
				throw new GatewayException(
					"Não foi possível carregar o arquivo " + file + ". Ele deve estar na pasta src do projeto"
				);
			}
			this.load(inStream);
		} catch (IOException e) {
			throw new GatewayException(
				"Não foi possível carregar o arquivo " + file + ". Ocorreu um erro de I/O",
				e
			);
		}
		ambiente = getAmbiente();
	}

	public static PropertiesUtil of() throws Exception {
		return new PropertiesUtil();
	}
	
	public static PropertiesUtil of(String file) throws Exception {
		return new PropertiesUtil(file);
	}

	public String get(String key) {
		return getProperty(key);
	}

	public String getIdentificador() {
		return get(String.format(GATEWAY_IDENTIFICADOR, ambiente));
	}

	public EAmbiente getAmbiente() {
		String ambiente = get(GATEWAY_AMBIENTE);
		return EAmbiente.valueOf(ambiente);
	}

}
