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
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.service.GatewayService;
import br.com.flexait.gateway.service.GatewayServiceTest;

@SuppressWarnings("unused")
public class IntegracaoTest {

	private static final int BIN_CARTAO = 545301;
	private static final String IDENTIFICACAO_ESAB = "4291989";
	private static final String IDENTIFICACAO = "1006993069";
	
	private static final String CHAVE_AUTENTICACAO = "25fbb99741c739dd84d7b06ec78c9bac718838630f30b112d033ce2e621b34f3";
	private static final String NUMERO_CARTAO_MASTERCARD = "4551870000000183";
	private static final String NUMERO_CARTAO_VISA = "5453010000066167";
	
	GatewayService service;
	Parametros params;
	
	@Before
	public void setUp() throws Exception {
		params = getParametrosRegistro();
		
		service = spy(GatewayServiceTest.getService());
	}

	@After
	public void tearDown() throws Exception {
		params = null;
		service = null;
	}

	private Parametros getParametrosRegistro() {
		Parametros params = Parametros.of();
		params.setIdentificacao(IDENTIFICACAO);
		params.setModulo(EModulo.CIELO);
		params.setOperacao(EOperacao.AutorizacaoDireta);
		params.setAmbiente(EAmbiente.TESTE);
		params.setBinCartao(BIN_CARTAO);
		params.setIdioma(EIdioma.PT);
		params.setValor(10.0);
		params.setPedido(1L);
		params.setDescricao("Pedido de teste");
		params.setBandeira(EBandeira.visa);
		params.setFormaPagamento(EFormaPagamento.CreditoAVista);
		params.setParcelas(1);
		params.setAutorizar(EAutorizar.AutorizarSemPassarPorAutenticacao);
		params.setCapturar(false);
		params.setCampoLivre("Transação de teste de integração ESAB");
		
		params.setNomePortadorCartao("NOME PORTADOR");
		params.setNumeroCartao(NUMERO_CARTAO_VISA);
		params.setValidadeCartao("201512");
		params.setIdentificadorCartao(EIdentificadorCartao.Informado);
		params.setCodigoSegurancaCartao(555);
		
		return params;
	}
	
	@Test
	public void deveRegistrarTransacao() throws Exception {
		System.out.println(params.getHttpParameters());
		
		Retorno retorno = service.post(params);
		
		System.out.println(retorno);
		
		verify(service).configScheme(Mockito.any(HttpClient.class));
		
		assertNotNull("Deve retornar transação sem erro", retorno.getTransacao());
	}
	
	

}
