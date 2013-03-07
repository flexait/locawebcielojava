package br.com.flexait.gateway.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.flexait.gateway.enums.TipoErroTest;
import br.com.flexait.gateway.integracao.IntegracaoTest;
import br.com.flexait.gateway.model.ErroTest;
import br.com.flexait.gateway.model.ParametrosTest;
import br.com.flexait.gateway.service.GatewayServiceTest;
import br.com.flexait.gateway.util.NumberUtilTest;
import br.com.flexait.gateway.util.PropertiesUtilTest;
import br.com.flexait.gateway.xml.ParserTest;

@RunWith(Suite.class)
@SuiteClasses({
	ParametrosTest.class,
	GatewayServiceTest.class,
	ParserTest.class,
	TipoErroTest.class,
	ErroTest.class,
	NumberUtilTest.class,
	IntegracaoTest.class,
	PropertiesUtilTest.class
})
public class AllTests {

}