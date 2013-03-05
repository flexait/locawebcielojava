package br.com.flexait.gateway.integracao;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.enums.EAutorizar;
import br.com.flexait.gateway.enums.EBandeira;
import br.com.flexait.gateway.enums.EFormaPagamento;
import br.com.flexait.gateway.enums.EIdioma;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.service.GatewayService;

@SuppressWarnings("unused")
public class IntegracaoTest {

	private static final int BIN_CARTAO = 464646;
	private static final String IDENTIFICACAO = "1006993069";
	
	private static final String CHAVE_AUTENTICACAO = "25fbb99741c739dd84d7b06ec78c9bac718838630f30b112d033ce2e621b34f3";
	private static final String NUMERO_CARTAO_MASTERCARD = "4551 8700 0000 0183";
	private static final String NUMERO_CARTAO_VISA = "5453 0100 0006 6167";
	
	GatewayService service;
	Parametros params;
	
	@Before
	public void setUp() throws Exception {
		params = getParametrosRegistro();
		
		service = GatewayService.of(
			GatewayService.DEFAULT_URL_GATEWAY,
			IDENTIFICACAO,
			EModulo.CIELO,
			EAmbiente.TESTE
		);
		
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
		params.setOperacao(EOperacao.Registro);
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
		params.setCapturar(true);
		params.setCampoLivre("Transação de teste de integração ESAB");
		
		return params;
	}
	
	@Test
	public void deveRegistrarTransacao() throws GatewayException {
		Retorno retorno = service.post(params);
		
		assertNotNull(retorno);
//		assertEquals("Deve retornar transação sem erro", retorno.getTransacao().getStatus(), EStatus.Criada);
	}
	
	

}
