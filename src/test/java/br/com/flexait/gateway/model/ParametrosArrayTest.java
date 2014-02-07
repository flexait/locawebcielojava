package br.com.flexait.gateway.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.flexait.gateway.enums.EOperacao;

public class ParametrosArrayTest {

	ParametrosArray array;
	
	@Before
	public void setUp() throws Exception {
		array = ParametrosArray.of(null);
	}

	@After
	public void tearDown() throws Exception {
		array = null;
	}

	@Test
	public void deveAdicionarItemStringSeValido() {
		array.add("teste", "teste");
		assertFalse("deve adicionar teste", array.isEmpty());
	}
	
	@Test
	public void deveAdicionarItemIntSeValido() {
		array.add("teste", 1);
		assertFalse("deve adicionar 1", array.isEmpty());
	}
	
	@Test
	public void deveAdicionarItemDoubleSeValido() {
		array.add("teste", 1.0);
		assertFalse("deve adicionar 1.0", array.isEmpty());
	}
	
	@Test
	public void deveNaoItemStringSeInvalido() {
		array.add("", "teste");
		assertTrue("nao deve adicionar nome vazio", array.isEmpty());
		
		array.add("teste", "");
		assertTrue("nao deve adicionar valor vazio", array.isEmpty());
		
		array.add("teste", 0);
		assertTrue("nao deve adicionar valor 0", array.isEmpty());
		
		array.add("teste", 0.0);
		assertTrue("nao deve adicionar valor 0.0", array.isEmpty());
	}
	
	@Test
	public void deveAdicionarItemObjectSeValido() {
		array.add("teste", EOperacao.Autorizacao);
		assertFalse("deve adicionar objeto", array.isEmpty());
	}
	
	@Test
	public void deveNaoAdicionarItemObjectSeInvalido() {
		array.add("teste", null);
		assertTrue("deve não adicionar quando objeto null", array.isEmpty());
	}
	
	@Test
	public void deveCriarArray() {
		array = ParametrosArray.of(ParametrosTest.getParams());
		
		array.createParams();
		
		assertFalse("deve adicionar itens", array.isEmpty());
	}
	
	@Test
	public void deveEvitarNulo() {
		array = ParametrosArray.of(null);
	}

}
