package br.com.flexait.gateway.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.model.Erro;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.model.Transacao;

public class ParserTest {

	Parser parser;
	InputStream inputStream;
	
	@Test public void deveRetornarRetornoTransacaoDoXml() throws Exception {
		String xml = getXmlTransacao();
		InputStream inputStream = IOUtils.toInputStream(xml);
		Object object = Parser.of(inputStream).toObject();
		Transacao retorno = (Transacao) object;
		assertNotNull("Objeto deve ser retornardo", retorno);
	}
	
	@Test public void deveRetornarErroDoXml() throws Exception {
		String xml = getXmlErro();
		InputStream inputStream = IOUtils.toInputStream(xml);
		Object object = Parser.of(inputStream).toObject();
		Erro erro = (Erro) object;
		assertNotNull("Objeto deve ser retornardo", erro);	
	}
	
	@Test public void deveRetornarObjetoRetorno() throws GatewayException, Exception {
		Retorno retorno = getRetornoTransacao();
		assertNotNull("Objeto deve ser retornardo", retorno.getTransacao());
		assertNull("Objeto deve ser null", retorno.getErro());
		
		retorno = getRetornoErro();
		assertNull("Objeto deve ser null", retorno.getTransacao());
		assertNotNull("Objeto deve ser retornardo", retorno.getErro());
	}
	
	@Test public void deveRetornarValorNormalizado() throws Exception {
		String xml = getXmlTransacao();
		InputStream inputStream = IOUtils.toInputStream(xml);
		Transacao t = (Transacao) Parser.of(inputStream).toObject();
		assertEquals("deve retornar 1.01", 1.01,  t.getDadosPedido().getValor(), 0.01);
	}

	public static Retorno getRetornoErro() throws Exception, IOException {
		String xml;
		Retorno retorno;
		xml = getXmlErro();
		retorno = Parser.of(IOUtils.toInputStream(xml)).getRetorno();
		return retorno;
	}

	public static Retorno getRetornoTransacao() throws Exception, IOException {
		String xml = getXmlTransacao();
		Retorno retorno = Parser.of(IOUtils.toInputStream(xml)).getRetorno();
		return retorno;
	}

	public static String getXmlErro() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		sb.append("<erro>");
		sb.append("<codigo>999</codigo>");
		sb.append("<mensagem>Valor nao deve conter ponto nem virgula. Formato 1000 para R$10,00</mensagem>");
		sb.append("</erro>");
		
		return sb.toString();
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

}
