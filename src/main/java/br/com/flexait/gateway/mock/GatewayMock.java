package br.com.flexait.gateway.mock;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.interfaces.IGatewayService;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.service.ParametrosValidate;
import br.com.flexait.gateway.util.PropertiesUtil;
import br.com.flexait.gateway.xml.Parser;

public class GatewayMock implements IGatewayService {
	
	private ParametrosValidate validate;

	protected static Retorno OK() throws GatewayException, Exception {
		return getRetornoTransacao();
	}
	
	public static Retorno getRetornoTransacao() throws IOException, Exception {
		String xml = getXmlTransacao();
		Retorno retorno = Parser.of(IOUtils.toInputStream(xml)).getRetorno();
		return retorno;
	}
	
	public static String getXmlTransacao() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>");
		sb.append("<transacao versao=\"1.0.0\" id=\"1\" xmlns=\"http://ecommerce.cbmp.com.br\">");
		sb.append("<tid>10017348980401201001</tid>");
		sb.append("<dados-pedido>");
		sb.append("<numero>1</numero>");
		sb.append("<valor>101</valor>");
		sb.append("<moeda>986</moeda>");
		sb.append("<data-hora>2010-04-27T17:49:50.120-03:00</data-hora>");
		sb.append("<descricao>Pedido de teste</descricao>");
		sb.append("<idioma>PT</idioma>");
		sb.append("</dados-pedido>");
		sb.append("<forma-pagamento>");
		sb.append("<bandeira>visa</bandeira>");
		sb.append("<produto>1</produto>");
		sb.append("<parcelas>1</parcelas>");
		sb.append("</forma-pagamento>");
		sb.append("<status>1</status>");
		sb.append("<autenticacao>");
		sb.append("<codigo>9</codigo>");
		sb.append("<mensagem>Transacao sem autenticacao</mensagem>");
		sb.append("<data-hora>2010-04-27T18:35:00.454-03:00</data-hora>");
		sb.append("<valor>100</valor>");
		sb.append("<eci>7</eci>");
		sb.append("</autenticacao>");
		sb.append("<autorizacao>");
		sb.append("<codigo>9</codigo>");
		sb.append("<mensagem>Transação autorizada</mensagem>");
		sb.append("<data-hora>2010-04-27T18:35:00.502-03:00</data-hora>");
		sb.append("<valor>100</valor>");
		sb.append("<lr>00</lr>");
		sb.append("<arp>183501</arp>");
		sb.append("</autorizacao>");
		sb.append("<url-autenticacao>https://qasecommerce.cielo.com.br/web/index.cbmp?id=d75b93d1edbc1a84fcf716ba727c4c5c</url-autenticacao>");
		sb.append("</transacao>");
		
		return sb.toString();
	}

	protected static Retorno ERRO() throws GatewayException, Exception {
		return getRetornoErro();
	}
	
	private static Retorno getRetornoErro() throws IOException, Exception {
		String xml;
		Retorno retorno;
		xml = getXmlErro();
		retorno = Parser.of(IOUtils.toInputStream(xml)).getRetorno();
		return retorno;
	}

	private static String getXmlErro() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		sb.append("<erro>");
		sb.append("<codigo>999</codigo>");
		sb.append("<mensagem>Valor nao deve conter ponto nem virgula. Formato 1000 para R$10,00</mensagem>");
		sb.append("</erro>");
		
		return sb.toString();
	}

	protected Retorno check(boolean valid) throws Exception {
		if(valid) {
			return OK();
		}
		return ERRO();
	}

	@Override
	public Retorno autorizacaoDireta(Parametros params) throws Exception {
		params.setOperacao(EOperacao.AutorizacaoDireta);
		return check(getPropertiesAndValidateTransacao(params));
	}

	@Override
	public Retorno consultar(Parametros params) throws Exception {
		params.setOperacao(EOperacao.Consulta);
		return check(getPropertiesAndValidateTid(params));
	}

	@Override
	public Retorno capturar(Parametros params) throws Exception {
		params.setOperacao(EOperacao.Captura);
		return check(getPropertiesAndValidateTid(params));
	}

	@Override
	public Retorno cancelar(Parametros params) throws Exception {
		params.setOperacao(EOperacao.Cancelamento);
		return check(getPropertiesAndValidateTid(params));
	}
	
	private Parametros getPropertiesParameters(Parametros params) throws Exception {
		PropertiesUtil util = PropertiesUtil.of();
		params.setAmbiente(util.getAmbiente());
		params.setIdentificacao(util.getIdentificador());
		params.setModulo(util.getModulo());
		return params;
	}
	
	private boolean getPropertiesAndValidateTransacao(Parametros params) throws Exception {
		params = getPropertiesParameters(params);
		validate = ParametrosValidate.of(params);
		return validate.validateAutorizacaoGroup();
	}

	private boolean getPropertiesAndValidateTid(Parametros params) throws Exception {
		params = getPropertiesParameters(params);
		validate = ParametrosValidate.of(params);
		return validate.validateTidGroup();
	}

	public static GatewayMock of() {
		return new GatewayMock();
	}
	
}
