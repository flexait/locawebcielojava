package br.com.flexait.gateway.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.ParametrosTest;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.xml.ParserTest;

public class GatewayServiceTest {

	
	public static final String IDENTIFICACAO = "4291989";
	GatewayService gatewayService;
	Parametros params;
	HttpResponse httpResponse;
	@Mock HttpClient httpClient;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		params = ParametrosTest.getParams();
		gatewayService = getService();
		gatewayService.setParametros(params);
		
		httpResponse = prepareResponse(200, ParserTest.getXmlTransacao());
		when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(httpResponse);
		gatewayService.setHttpClient(httpClient);
	}

	@After
	public void tearDown() throws Exception {
		gatewayService = null;
		params = null;
		httpResponse = null;
	}

	@Test public void deveConfigurarDadosParamsQuandoSetParams() throws GatewayException {
		Parametros paramsSpy = spy(params);
		
		GatewayService serviceSpy = spy(gatewayService);
		
		serviceSpy.setParametros(paramsSpy);
		
		verify(paramsSpy).setIdentificacao(Mockito.anyString());
		verify(paramsSpy).setModulo(Mockito.any(EModulo.class));
		verify(paramsSpy).setAmbiente(Mockito.any(EAmbiente.class));
		
		assertEquals("Deve ter numero identificacao", IDENTIFICACAO, serviceSpy.getParametros().getIdentificacao());
		assertEquals("Deve ter numero identificacao", ParametrosTest.CIELO, serviceSpy.getParametros().getModulo());
		assertEquals("Deve ter numero identificacao", ParametrosTest.TESTE, serviceSpy.getParametros().getAmbiente());
	}
	
	@Test public void deveConfigurarHttpClient() {
		assertNotNull("HttpClient não pode ser nulo", gatewayService.getHttpClient());
		assertNotNull("HttpClient não pode ser nulo", gatewayService.getHttpPost());
	}
	
	@Test public void deveConfigurarPostQuandoConfiguradaUrl() {
		String url = "http://localhost:8080";
		gatewayService.setUrl(url);
		
		assertEquals("Deve modificar o post quando modificar a url", "localhost",
				gatewayService.getHttpPost().getURI().getHost());
	}
	
	@Test public void deveExecutarPost() throws GatewayException {
		gatewayService = spy(gatewayService);
		
		Retorno retorno = gatewayService.post(params);
		assertNotNull("Deve retornar objeto deserializado", retorno.getTransacao());
		
		verify(gatewayService).setParametros(Mockito.any(Parametros.class));
	}
	
	private HttpResponse prepareResponse(int expectedResponseStatus,
			String expectedResponseBody) {
		HttpResponse response = new BasicHttpResponse(new BasicStatusLine(
				new ProtocolVersion("HTTP", 1, 1), expectedResponseStatus, ""));
		response.setStatusCode(expectedResponseStatus);
		try {
			response.setEntity(new StringEntity(expectedResponseBody));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
		return response;
	}

	private GatewayService getService() {
		GatewayService service = GatewayService.of(
			GatewayService.DEFAULT_URL_GATEWAY,
			IDENTIFICACAO,
			ParametrosTest.CIELO,
			ParametrosTest.TESTE
		);
		
		return service;
	}
}
