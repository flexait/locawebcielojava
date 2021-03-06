package br.com.flexait.gateway.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.enums.EBandeira;
import br.com.flexait.gateway.enums.EFormaPagamento;
import br.com.flexait.gateway.enums.EIndicadorCartao;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.model.Parametros;


public class ParametrosValidateTest {

	ParametrosValidate validate;
	
	@Before
	public void setUp() throws Exception {
		validate = ParametrosValidate.of(GatewayServiceTest.getParametrosRegistro());
	}
	
	@After
	public void tearDown() {
		validate = null;
	}
	
	@Test
	public void deveRetornarHibernateValidator() {
		assertNotNull("deve retornar validator", validate.getValidator());
	}
	
	@Test public void deveValidarBeanTidGroup() throws Exception {
		Parametros parametros = GatewayServiceTest.getParametrosRegistro();
		parametros.setTid(null);
		
		validate = ParametrosValidate.of(parametros);
		assertFalse("deve ser invalido", validate.validateTidGroup());
		
		parametros.setTid("123");
		validate = ParametrosValidate.of(parametros);
		assertTrue("deve ser invalido", validate.validateTidGroup());
	}
	
	@Test public void deveValidarDefaultGroup() throws Exception {
		Parametros parametros = GatewayServiceTest.getParametrosRegistro();
		parametros.setIdentificacao(null);
		
		validate = ParametrosValidate.of(parametros);
		assertFalse("deve ser invalido", validate.validateDefaultGroup());
	}
	
	@Test public void deveValidarAutorizacaoDiretaGroup() throws Exception {
		Parametros parametros = GatewayServiceTest.getParametrosRegistro();
		parametros.setNumeroCartao(null);
		
		validate = ParametrosValidate.of(parametros);
		assertFalse("deve retornar false com numero cartão nulo", validate.validateAutorizacaoDiretaGroup());
		
		parametros = Parametros.of();
		parametros.setIdentificacao(GatewayServiceTest.IDENTIFICACAO);
		parametros.setModulo(EModulo.CIELO);
		parametros.setOperacao(EOperacao.AutorizacaoDireta);
		parametros.setAmbiente(EAmbiente.TESTE);
		
		parametros.setValor(10.0);
		parametros.setPedido(1L);
		parametros.setBandeira(EBandeira.visa);
		parametros.setFormaPagamento(EFormaPagamento.CreditoAVista);
		parametros.setParcelas(1);
		
		parametros.setNomePortadorCartao("Teste");
		parametros.setNumeroCartao(GatewayServiceTest.NUMERO_CARTAO_VISA);
		parametros.setValidadeCartao("202212");
		parametros.setIndicadorCartao(EIndicadorCartao.Ilegivel);
		parametros.setCodigoSegurancaCartao(null);
		
		validate = ParametrosValidate.of(parametros);
		
		assertTrue("deve retornar true com os dados validos e codigo segurança null com ilegível", validate.validateAutorizacaoGroup());
		
		parametros.setValidadeCartao("200000");
		
		validate = ParametrosValidate.of(parametros);
		assertFalse(validate.validateAutorizacaoDiretaGroup());
	}
	
	@Test public void deveValidarAutorizacaoGroup() throws Exception {
		Parametros parametros = GatewayServiceTest.getParametrosRegistro();
		parametros.setNumeroCartao(null);
		
		validate = ParametrosValidate.of(parametros);
		assertFalse("deve retornar false com numero cartão nulo", validate.validateAutorizacaoDiretaGroup());
		
		parametros = Parametros.of();
		parametros.setIdentificacao(GatewayServiceTest.IDENTIFICACAO);
		parametros.setModulo(EModulo.CIELO);
		parametros.setOperacao(EOperacao.AutorizacaoDireta);
		parametros.setAmbiente(EAmbiente.TESTE);
		
		parametros.setValor(10.0);
		parametros.setPedido(1L);
		parametros.setBandeira(EBandeira.visa);
		parametros.setFormaPagamento(EFormaPagamento.CreditoAVista);
		parametros.setParcelas(1);
		
		validate = ParametrosValidate.of(parametros);
		
		assertTrue("deve retornar true com os dados validos e codigo segurança null com ilegível", validate.validateAutorizacaoGroup());
		
		parametros.setValidadeCartao("200000");
		
		validate = ParametrosValidate.of(parametros);
		assertFalse(validate.validateAutorizacaoDiretaGroup());
	}
	
	@Test
	public void deveRetornarMensagensDeErroNormalizada() throws Exception {
		Parametros parametros = GatewayServiceTest.getParametrosRegistro();
		parametros.setValidadeCartao("201212");
		parametros.setValor(0.0);
		
		ParametrosValidate validate = ParametrosValidate.of(parametros);
		validate.validate();
		String erros = validate.getErros();
		assertTrue("deve retornar erro de validade", erros.contains("deve ser atual"));
		assertTrue("deve retornar erro de valor", erros.contains("deve ser maior ou igual a 1"));
	}
	
	@Test
	public void deveTratarMensagensNulas() throws Exception {
		Parametros params = Parametros.of(EOperacao.Cancelamento, null);
		ParametrosValidate validate = ParametrosValidate.of(params);
		validate.validate();
		GatewayService.log.debug(validate.getErros());
	}
	
}
