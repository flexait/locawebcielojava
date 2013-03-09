package br.com.flexait.gateway.model;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.enums.EAutorizar;
import br.com.flexait.gateway.enums.EBandeira;
import br.com.flexait.gateway.enums.EFormaPagamento;
import br.com.flexait.gateway.enums.EIndicadorCartao;
import br.com.flexait.gateway.enums.EIdioma;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.integracao.IntegracaoTest;
import br.com.flexait.gateway.util.DateUtil;

public class ParametrosTest {

	public static final EAmbiente TESTE = EAmbiente.TESTE;
	public static final EModulo CIELO = EModulo.CIELO;
	public static final EAmbiente PRODUCAO = EAmbiente.PRODUCAO;
	
	Parametros params;
	
	@Before
	public void setUp() throws Exception {
		params = getParams();
	}

	@After
	public void tearDown() throws Exception {
	}

	public static Parametros getParams() {
		Parametros params = Parametros.of();
		params.setIdentificacao("");
		params.setModulo(CIELO);
		params.setOperacao(EOperacao.Registro);
		params.setAmbiente(TESTE);
		params.setBinCartao("454545");
		params.setIdioma(EIdioma.PT);
		params.setValor(10.0);
		params.setPedido(1L);
		params.setDescricao("Pedido de teste");
		params.setBandeira(EBandeira.mastercard);
		params.setAutorizar(EAutorizar.AutorizarSomenteAutenticada);
		params.setFormaPagamento(EFormaPagamento.Debito);
		params.setCapturar(true);
		params.setCampoLivre("Transação de teste");
		return params;
	}
	
	@Test public void deveEncodarUrl() throws GatewayException {
		params.setIdentificacao("123");
		params.setCampoLivre(null);
		params.setIdioma(null);
		params.setDescricao(null);
		params.setTid(null);
		List<NameValuePair> parameters = params.getHttpParameters();
		assertFalse("Uri não pode ser vazia", parameters.isEmpty());
		
		for (NameValuePair n: parameters) {
			assertNotNull("Não pode existir parametro nulo", n.getValue());
			assertFalse("Não pode existir parametro nulo", n.getValue().isEmpty());
		}
	}

	@Test public void deveRetornarTrueSeIdentificadorNaoInformado() {
		params.setBandeira(EBandeira.visa);
		params.setIndicadorCartao(EIndicadorCartao.Ilegivel);
		boolean codigoValid = params.isCodigoNotNullValid();
		assertTrue("deve ser true se identificador nao informado", codigoValid);
		
		params.setIndicadorCartao(EIndicadorCartao.Inexistente);
		codigoValid = params.isCodigoNotNullValid();
		assertTrue("deve ser true se identificador nao informado", codigoValid);
		
		params.setIndicadorCartao(EIndicadorCartao.NaoInformado);
		codigoValid = params.isCodigoNotNullValid();
		assertTrue("deve ser true se identificador nao informado", codigoValid);
	}
	
	@Test public void deveRetornarTrueSeIdentificadorInformado() {
		params.setIndicadorCartao(EIndicadorCartao.Informado);
		params.setCodigoSegurancaCartao("1");
		boolean codigoValid = params.isCodigoNotNullValid();
		assertTrue("deve ser true se identificador nao informado", codigoValid);
	}
	
	@Test public void deveRetornarFalseSeIdentificarNaoInformado() {
		params.setIndicadorCartao(EIndicadorCartao.Informado);
		params.setCodigoSegurancaCartao(null);
		
		boolean codigoValid = params.isCodigoNotNullValid();
		assertFalse("deve ser true se identificador nao informado", codigoValid);
		
		params.setBandeira(EBandeira.visa);
		params.setIndicadorCartao(EIndicadorCartao.NaoInformado);
		codigoValid = params.isCodigoNotNullValid();
		assertTrue("deve ser true se identificador nao informado", codigoValid);
	}
	
	@Test public void deveValidarPatternCodigo() {
		boolean matches = "11a".matches(Parametros.PATTERN_CODIGO_SEGURANCA);
		assertFalse("deve ser false com letra", matches);
	}
	
	@Test public void deveValidarPatternCartao() {
		boolean matches = IntegracaoTest.NUMERO_CARTAO_MASTERCARD.matches(Parametros.PATTERN_NUMERO_CARTAO);
		assertTrue("deve ser numero de cartão de credito visa ou mastercard", matches);
		
		matches = IntegracaoTest.NUMERO_CARTAO_VISA.matches(Parametros.PATTERN_NUMERO_CARTAO);
		assertTrue("deve ser numero de cartão de credito visa ou mastercard", matches);
		
		matches = "6453010000066167".matches(Parametros.PATTERN_NUMERO_CARTAO);
		assertFalse("deve ser numero de cartão de credito visa ou mastercard", matches);
	}
	
	@Test public void deveValidarValidadeCartao() {
		boolean matches = "200013".matches(Parametros.PATTERN_VALIDADE);
		assertFalse("deve ser data valida no formato aaaamm", matches);
		
		matches = "200000".matches(Parametros.PATTERN_VALIDADE);
		assertFalse("deve ser data valida no formato aaaamm", matches);
		
		matches = "200001".matches(Parametros.PATTERN_VALIDADE);
		assertTrue("deve ser data valida no formato aaaamm", matches);
		
		matches = "200013".matches(Parametros.PATTERN_VALIDADE);
		assertFalse("deve ser data valida no formato aaaamm", matches);
		
		matches = "300013".matches(Parametros.PATTERN_VALIDADE);
		assertFalse("deve ser data valida no formato aaaamm", matches);
		
		matches = "100013".matches(Parametros.PATTERN_VALIDADE);
		assertFalse("deve ser data valida no formato aaaamm", matches);
		
		matches = "220013".matches(Parametros.PATTERN_VALIDADE);
		assertFalse("deve ser data valida no formato aaaamm", matches);
	}
	
	@Test public void deveTratarValorFormaPagamento() {
		String string = params.getFormaPagamentoString();
		assertNotNull("deve retornar valor quando não nulo", string);
		
		params.setFormaPagamento(null);
		string = params.getFormaPagamentoString();
		assertNull("deve retornar null quando nulo", string);
	}
	
	@Test public void deveTratarValorAutorizar() {
		String string = params.getAutorizarString();
		assertNotNull("deve retornar valor quando não nulo", string);
		
		params.setAutorizar(null);
		string = params.getAutorizarString();
		assertNull("deve retornar null quando nulo", string);
	}
	
	@Test public void deveTratarValorIndicador() {
		params.setIndicadorCartao(EIndicadorCartao.Informado);
		String string = params.getIndicadorCartaoString();
		assertNotNull("deve retornar valor quando não nulo", string);
		
		params.setIndicadorCartao(null);
		string = params.getIndicadorCartaoString();
		assertNull("deve retornar null quando nulo", string);
	}
	
	@Test public void deveTratarTipoCartao() {
		params.setBandeira(null);
		params.setNumeroCartao(null);
		assertTrue("se nulos retorna true", params.isNumeroCartaoValid());
		
		params.setBandeira(EBandeira.mastercard);
		params.setNumeroCartao(IntegracaoTest.NUMERO_CARTAO_MASTERCARD);
		assertTrue("cartao master deve iniciar por 5", params.isNumeroCartaoValid());
		
		params.setBandeira(EBandeira.visa);
		params.setNumeroCartao(IntegracaoTest.NUMERO_CARTAO_VISA);
		assertTrue("cartao master deve iniciar por 5", params.isNumeroCartaoValid());
		
		params.setBandeira(EBandeira.visa);
		params.setNumeroCartao(IntegracaoTest.NUMERO_CARTAO_MASTERCARD);
		assertFalse("cartao master deve iniciar por 5", params.isNumeroCartaoValid());
		
		params.setBandeira(EBandeira.mastercard);
		params.setNumeroCartao(IntegracaoTest.NUMERO_CARTAO_VISA);
		assertFalse("cartao master deve iniciar por 5", params.isNumeroCartaoValid());
	}
	
	@Test public void deveTratarValidadeCartao() {
		params.setValidadeCartao(null);
		assertTrue("deve tratar valor nulo", params.isValidadeCartaoValid());
		
		Calendar cal = Calendar.getInstance();
		
		params.setValidadeCartao(String.valueOf(DateUtil.getDataAnoMes(cal)));
		assertTrue("deve igual a " + DateUtil.getDataAnoMes(cal), params.isValidadeCartaoValid());
		
		params.setValidadeCartao("200001");
		assertFalse("deve maior que hoje", params.isValidadeCartaoValid());
	}

}
