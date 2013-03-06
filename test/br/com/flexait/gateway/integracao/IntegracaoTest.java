package br.com.flexait.gateway.integracao;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.enums.EAutorizar;
import br.com.flexait.gateway.enums.EBandeira;
import br.com.flexait.gateway.enums.EFormaPagamento;
import br.com.flexait.gateway.enums.EIdentificadorCartao;
import br.com.flexait.gateway.enums.EIdioma;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.ParametrosTest;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.service.GatewayService;
import br.com.flexait.gateway.service.GatewayServiceTest;

@SuppressWarnings("unused")
public class IntegracaoTest {

	private static final String IDENTIFICACAO_ESAB = "4291989";
	public static final String IDENTIFICACAO = "1006993069";
	
	private static final String NUMERO_CARTAO_MASTERCARD = "5453010000066167";
	private static final String NUMERO_CARTAO_VISA = "4551870000000183";

	GatewayService service;
	Parametros params;
	
	@Before
	public void setUp() throws Exception {
		params = getParametrosRegistro();
		
		service = spy(getService());
	}

	@After
	public void tearDown() throws Exception {
		params = null;
		service = null;
	}

	private Parametros getParametrosRegistro() {
		String numeroCartao = NUMERO_CARTAO_MASTERCARD;
		String bin = numeroCartao.substring(0, 6);
		
		Parametros params = Parametros.of();
		params.setOperacao(EOperacao.AutorizacaoDireta);
		params.setBinCartao(bin);
		params.setIdioma(EIdioma.PT);
		params.setValor(1.00);
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
		params.setValidadeCartao("201712");
		params.setIndicadorCartao(EIdentificadorCartao.Informado);
		params.setCodigoSegurancaCartao("034");
		params.setTid("10069930690CDF4F1001");
		
		return params;
	}
	
	@Test
	public void deveAutorizarDiretamenteTransacao() throws Exception {
		params.setOperacao(EOperacao.AutorizacaoDireta);
		Retorno retorno = service.post(params);
		
		assertNotNull("Deve retornar transação sem erro", retorno.getTransacao());
		
		verify(service).configScheme(Mockito.any(HttpClient.class));
	}
	
	@Test
	public void deveCancelarTransacao() throws Exception {
		params.setOperacao(EOperacao.Cancelamento);
		Retorno retorno = service.post(params);
		
		assertNotNull("Deve retornar transação com erro devido aos dados do servidor", retorno.getErro());
	}
	
	@Test
	public void deveConsultaTransacao() throws Exception {
		params.setOperacao(EOperacao.Consulta);
		Retorno retorno = service.post(params);
		
		assertNotNull("Deve retornar transação sem erro", retorno.getTransacao());
	}
	
	private static GatewayService getService() {
		GatewayService service = GatewayService.of(
			GatewayService.DEFAULT_URL_GATEWAY,
			IntegracaoTest.IDENTIFICACAO_ESAB,
			ParametrosTest.CIELO,
			ParametrosTest.TESTE
		);
		
		return service;
	}

}
