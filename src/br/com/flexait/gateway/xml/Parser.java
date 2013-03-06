package br.com.flexait.gateway.xml;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.model.Erro;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.model.Transacao;
import br.com.flexait.gateway.service.GatewayService;

import com.thoughtworks.xstream.XStream;

public class Parser {
	
	private XStream xstream;
	private InputStream is;
	
	Parser(InputStream is) {
		construct();
		this.is = is;
	}
	
	private void construct() {
		xstream = new XStream();
	}
	
	public static Parser of(InputStream is) throws IOException {
		return new Parser(is);
	}
	
	protected Object toObject() throws Exception {
		
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(Transacao.class);
		xstream.processAnnotations(Erro.class);
		
		String toString = IOUtils.toString(is, "ISO-8859-1");
		
		GatewayService.log.debug("\nXML de resposta:\n" + toString);
		
		return xstream.fromXML(toString);
	}

	public Retorno getRetorno() throws Exception {
		Object object = toObject();
		
		Transacao transacao = null;
		Erro erro = null;
		
		Class<? extends Object> type = object.getClass();
		if(type.isAssignableFrom(Transacao.class)) {
			transacao = (Transacao) object;
		}
		else if(type.isAssignableFrom(Erro.class)) {
			erro = (Erro) object;
		}
		else {
			throw new GatewayException("Não é possível deserializar o xml");
		}
		
		return Retorno.of(transacao, erro);
	}
	
}
