package br.com.flexait.gateway.xml;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.model.Erro;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.model.Transacao;
import br.com.flexait.gateway.service.GatewayService;
import br.com.flexait.gateway.util.PropertiesUtil;

import com.thoughtworks.xstream.XStream;

public class Parser {
	
	private XStream xstream;
	private InputStream is;
	
	Parser(InputStream is) {
		xstream = new XStream();
		this.is = is;
	}
	
	public static Parser of(InputStream is) throws IOException {
		return new Parser(is);
	}
	
	protected Object toObject() throws Exception {
		
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(Transacao.class);
		xstream.processAnnotations(Erro.class);
		
		String toString = IOUtils.toString(is, PropertiesUtil.of().getEncode());
		
		GatewayService.log.debug("XML de resposta: " + toString);
		Object fromXML = null;
		
		try {
			fromXML = xstream.fromXML(toString);
		} catch (Exception e) {
			GatewayService.log.error(e);
			
			Erro erro = new Erro();
			erro.setCodigo("EINV");
			erro.setMensagem("Erro de parse do xml: " + toString);
			erro.setDetalhes("EINV");
			return erro;					
		}
		
		return fromXML;
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
			String erroSerializacao = "Não é possível deserializar o xml";
			GatewayService.log.error(erroSerializacao);
			throw new GatewayException(erroSerializacao);
		}
		
		return Retorno.of(transacao, erro);
	}
	
}
