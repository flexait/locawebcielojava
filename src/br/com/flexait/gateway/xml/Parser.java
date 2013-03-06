package br.com.flexait.gateway.xml;

import java.io.InputStream;

import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.model.Erro;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.model.Transacao;

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
	
	public static Parser of(InputStream is) {
		return new Parser(is);
	}
	
	protected Object toObject() {
		
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(Transacao.class);
		xstream.processAnnotations(Erro.class);
		
		return xstream.fromXML(is);
	}

	public Retorno getRetorno() throws GatewayException {
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
