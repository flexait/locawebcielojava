package br.com.flexait.gateway.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.flexait.gateway.error.Erro001;
import br.com.flexait.gateway.error.Erro002;
import br.com.flexait.gateway.error.Erro003;
import br.com.flexait.gateway.error.Erro010;
import br.com.flexait.gateway.error.Erro011;
import br.com.flexait.gateway.error.Erro012;
import br.com.flexait.gateway.error.Erro020;
import br.com.flexait.gateway.error.Erro021;
import br.com.flexait.gateway.error.Erro022;
import br.com.flexait.gateway.error.Erro030;
import br.com.flexait.gateway.error.Erro031;
import br.com.flexait.gateway.error.Erro032;
import br.com.flexait.gateway.error.Erro033;
import br.com.flexait.gateway.error.Erro040;
import br.com.flexait.gateway.error.Erro041;
import br.com.flexait.gateway.error.Erro042;
import br.com.flexait.gateway.error.Erro099;

public class ErroTest {

	Erro erro;
	
	@Before
	public void setUp() throws Exception {
		erro = new Erro();
	}

	@After
	public void tearDown() throws Exception {
		erro = null;
	}

	@Test
	public void deveRetornarErroCorreto() {
		
		erro.setCodigo("001");
		assertEquals("Erro deve ser 001", Erro001.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("002");
		assertEquals("Erro deve ser 002", Erro002.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("003");
		assertEquals("Erro deve ser 003", Erro003.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("010");
		assertEquals("Erro deve ser 010", Erro010.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("011");
		assertEquals("Erro deve ser 011", Erro011.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("012");
		assertEquals("Erro deve ser 012", Erro012.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("020");
		assertEquals("Erro deve ser 020", Erro020.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("021");
		assertEquals("Erro deve ser 021", Erro021.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("022");
		assertEquals("Erro deve ser 022", Erro022.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("030");
		assertEquals("Erro deve ser 030", Erro030.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("031");
		assertEquals("Erro deve ser 031", Erro031.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("032");
		assertEquals("Erro deve ser 032", Erro032.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("033");
		assertEquals("Erro deve ser 033", Erro033.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("040");
		assertEquals("Erro deve ser 040", Erro040.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("041");
		assertEquals("Erro deve ser 041", Erro041.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("042");
		assertEquals("Erro deve ser 042", Erro042.class, erro.getDetalhes().getClass());
		
		erro.setCodigo("099");
		assertEquals("Erro deve ser 099", Erro099.class, erro.getDetalhes().getClass());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deveRetornarExceptionParaErroInexistente() {
		erro.setCodigo("555");
	}

}
