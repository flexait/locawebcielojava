package br.com.flexait.gateway.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
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
import br.com.flexait.gateway.enums.EAutorizar;
import br.com.flexait.gateway.enums.EBandeira;
import br.com.flexait.gateway.enums.EFormaPagamento;
import br.com.flexait.gateway.enums.EIdioma;
import br.com.flexait.gateway.enums.EIndicadorCartao;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.ParametrosTest;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.xml.ParserTest;

public class GatewayServiceTest {

	public static final String IDENTIFICACAO = "1006993069";
	
	public static final String NUMERO_CARTAO_MASTERCARD = "5453010000066167";
	public static final String NUMERO_CARTAO_VISA = "4551870000000183";
	
	GatewayService gatewayService;
	Parametros params;
	HttpResponse httpResponse;
	@Mock HttpClient httpClient;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		params = getParametrosRegistro();
		gatewayService = spy(getService());
		gatewayService.setParametros(params);
		
		httpResponse = prepareResponse(200, ParserTest.getXmlTransacao());
		when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(httpResponse);
		gatewayService.setHttpClient(httpClient);
		
		doReturn(httpClient).when(gatewayService).configScheme(Mockito.any(HttpClient.class));
	}

	@After
	public void tearDown() throws Exception {
		gatewayService = null;
		params = null;
		httpResponse = null;
	}

	@Test public void deveConfigurarDadosParamsQuandoSetParams() throws GatewayException {
		params = spy(params);
		
		gatewayService.setParametros(params);
		
		verify(params).setIdentificacao(Mockito.anyString());
		verify(params).setModulo(Mockito.any(EModulo.class));
		verify(params).setAmbiente(Mockito.any(EAmbiente.class));
		
		assertEquals("Deve ter numero identificacao", IDENTIFICACAO, gatewayService.getParametros().getIdentificacao());
		assertEquals("Deve ter numero identificacao", ParametrosTest.CIELO, gatewayService.getParametros().getModulo());
		assertEquals("Deve ter numero identificacao", ParametrosTest.TESTE, gatewayService.getParametros().getAmbiente());
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
	
	@Test public void deveExecutarPost() throws GatewayException, Exception {
		Retorno retorno = gatewayService.post(getParametrosRegistro());
		assertNotNull("Deve retornar objeto deserializado", retorno.getTransacao());
		
		verify(gatewayService).validateParams(Mockito.any(Parametros.class));
	}
	
	@Test public void deveExecutarAutorizacaoDireta() throws GatewayException {
		Retorno retorno = gatewayService.autorizacaoDireta(params);
		assertNotNull("Deve retornar objeto deserializado", retorno.getTransacao());
	}
	
	@Test
	public void deveAutorizarDiretamenteTransacao() throws Exception {
		Retorno retorno = gatewayService.autorizacaoDireta(params);
		
		assertEquals("Operação deve ser Autorizacao", EOperacao.AutorizacaoDireta, retorno.getOperacao());
		assertNotNull("Deve retornar transação sem erro", retorno.getTransacao());
	}
	
	@Test public void deveExecutarAutorizacao() throws GatewayException {
		params.setOperacao(EOperacao.Autorizacao);
		Retorno retorno = gatewayService.autorizacao(params);
		assertNotNull("Deve retornar objeto deserializado", retorno.getTransacao());
	}
	
	@Test
	public void deveAutorizarTransacao() throws Exception {
		Retorno retorno = gatewayService.autorizacao(params);
		
		assertEquals("Operação deve ser Autorizacao", EOperacao.Autorizacao, retorno.getOperacao());
		assertNotNull("Deve retornar transação sem erro", retorno.getTransacao());
	}
	
	@Test
	public void deveConsultaTransacao() throws Exception {
		Retorno retorno = gatewayService.consultar(params);
		
		assertEquals("Operação deve ser consulta", EOperacao.Consulta, retorno.getOperacao());
		assertNotNull("Deve retornar transação sem erro", retorno.getTransacao());
	}
	
	@Test
	public void deveCapturarTransacao() throws Exception {
		Retorno retorno = gatewayService.capturar(params);
		
		assertEquals("Operação deve ser consulta", EOperacao.Captura, retorno.getOperacao());
		assertNotNull("Deve retornar transação com erro pois a transação de teste está cancelada", retorno.getTransacao());
	}
	
	@Test
	public void deveCancelarTransacao() throws Exception {
		Retorno retorno = gatewayService.cancelar(params);
		
		assertEquals("Operação deve ser consulta", EOperacao.Cancelamento, retorno.getOperacao());
		assertNotNull("Deve retornar transação com erro devido aos dados do servidor", retorno.getTransacao());
	}
	
	@Test public void deveValidarParametros() throws Exception {
		params = getParametrosRegistro();
		boolean valid = gatewayService.validateParams(params);
		assertTrue("deve retornar true na validação", valid);
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
	
	public static Parametros getParametrosRegistro() throws Exception {
		String numeroCartao = NUMERO_CARTAO_MASTERCARD;
		String bin = numeroCartao.substring(0, 6);
		
		Parametros params = Parametros.of();
		
		params.setIdentificacao(IDENTIFICACAO);
		
		params.setAmbiente(EAmbiente.TESTE);
		params.setModulo(EModulo.CIELO);
		params.setOperacao(EOperacao.AutorizacaoDireta);
		params.setBinCartao(bin);
		params.setIdioma(EIdioma.PT);
		params.setValor(10.00);
		params.setPedido(1L);
		params.setDescricao("Pedido de teste");
		params.setBandeira(EBandeira.mastercard);
		params.setFormaPagamento(EFormaPagamento.CreditoAVista);
		params.setParcelas(1);
		params.setAutorizar(EAutorizar.AutorizarAutenticadaENaoAutenticada);
		params.setCapturar(false);
		params.setCampoLivre("Transacao de teste de integracao ESAB");
		
		params.setNomePortadorCartao("INTEGRACAO TESTE");
		params.setNumeroCartao(numeroCartao);
		params.setValidadeCartao("202012");
		params.setIndicadorCartao(EIndicadorCartao.Informado);
		params.setCodigoSegurancaCartao("555");
		params.setTid("10069930690CDF4F1001");
		
		return params;
	}
}
