package br.com.flexait.gateway.model;

import static org.junit.Assert.*;

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
		params.setIndicadorCartao(EIndicadorCartao.Ilegivel);
		boolean codigoValid = params.isCodigoValid();
		assertTrue("deve ser true se identificador nao informado", codigoValid);
		
		params.setIndicadorCartao(EIndicadorCartao.Inexistente);
		codigoValid = params.isCodigoValid();
		assertTrue("deve ser true se identificador nao informado", codigoValid);
		
		params.setIndicadorCartao(EIndicadorCartao.NaoInformado);
		codigoValid = params.isCodigoValid();
		assertTrue("deve ser true se identificador nao informado", codigoValid);
	}
	
	@Test public void deveRetornarTrueSeIdentificadorInformado() {
		params.setIndicadorCartao(EIndicadorCartao.Informado);
		params.setCodigoSegurancaCartao("1");
		boolean codigoValid = params.isCodigoValid();
		assertTrue("deve ser true se identificador nao informado", codigoValid);
	}
	
	@Test public void deveRetornarFalseSeIdentificarNaoInformado() {
		params.setIndicadorCartao(EIndicadorCartao.Informado);
		params.setCodigoSegurancaCartao(null);
		
		boolean codigoValid = params.isCodigoValid();
		assertFalse("deve ser true se identificador nao informado", codigoValid);
		
		params.setIndicadorCartao(EIndicadorCartao.NaoInformado);
		codigoValid = params.isCodigoValid();
		assertTrue("deve ser true se identificador nao informado", codigoValid);
	}
	
	@Test public void deveValidarPatternCodigo() {
		boolean matches = "11a".matches(Parametros.PATTERN_CODIGO_SEGURANCA);
		assertFalse("deve ser false com letra", matches);
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

}
