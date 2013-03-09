LocawebCieloJava
================

Gateway de Pagamento

### 1) Uso básico da API

### 1.1) Construtores e Configurações

Para poder utilizar a API, basta fazer o download do jar disponível em https://github.com/flexait/LocawebCieloJava/tree/master/dist/ e adicioná-lo no seu classpath.

Para usar configurações padrões, basta usar o construtor estático of, sendo neste caso apenas para teste (prefira usar a interface para facilitar usar o mock quando precisar:
<pre>
IGatewayService GatewayService.of();
</pre>

Para usar a API em produção, você pode configurar um arquivo properties conforme explicado no tópico #3, assim você pode modificar o ambiente para PRODUCAO, e adicionar a configuração do seu número identificador no gateway.
Outra forma é usando o construtor estático com todos os parâmetros.
<pre>
IGatewayService GatewayService.of(String url, String identificacao, EModulo modulo, EAmbiente ambiente);
</pre>

### 1.2) Criando Parametros

### 1.2.1) Autorização Direta

A Documentação da Locaweb informa dois tipos de autorização, o registro e a autorização, e a autorização direta, que faz o registro no mesmo momento. Entretanto, o único método que funcionou nos testes foi a autorização direta, por este motivo recomendo que você o use.
Outro ponto importante, é que há relatos de que a captura automática também não funciona adequadamente, assim, prefira usar a autorização direta, mas sem captura automática, que será feita no segundo passo (#1.2.2).

Criando os parametros:

<pre>
Parametros params = Parametros.of(); //construtor
params.setOperacao(EOperacao.AutorizacaoDireta);
params.setBinCartao("545301"); //6 primeiros números do cartao
params.setIdioma(EIdioma.PT);
params.setValor(1.00);
params.setPedido(1L);
params.setDescricao("Pedido de teste");
params.setBandeira(EBandeira.mastercard);
params.setFormaPagamento(EFormaPagamento.CreditoAVista);
params.setParcelas(1);
params.setAutorizar(EAutorizar.AutorizarAutenticadaENaoAutenticada);
params.setCapturar(false);
params.setCampoLivre("Transacao de teste de integracao");
//dados do cartão	
params.setNomePortadorCartao("INTEGRACAO TESTE");
params.setNumeroCartao("5453010000066167"); //sem espaços
params.setValidadeCartao("202012"); //validade no formato aaaamm
params.setIndicadorCartao(EIdentificadorCartao.Informado);
params.setCodigoSegurancaCartao("555");
</pre>

Obs. 1: Não adianta configurar os parametros identificacao, modulo ou ambiente, pois os parâmetros serão sobrescritos pelos parametros do que foram construídos no service.
Obs. 2: Você pode usar a função estática que retorna uma instância de parametros configurados para teste, mas não esqueça de configurar a operação.
<pre>
Parametros IntegracaoTest.getParametrosRegistro();
params.setOperacao(EOperacao.AutorizacaoDireta);
</pre>
Obs. 3: Caso você esteja usando codificação UTF-8, deve configurar também o encode de parametros: EEnconde.UTF8, o padrão é EEncode.ISO88591

Executando o post:
<pre>
Retorno retorno = gatewayService.post(params);
</pre>

O objeto retorno possui dois atributos importantes, transacao e erro e apenas um dos dois será retornado preenchido.
O objeto erro retornará os detalhes do erro, já o transacao, os dados de retorno da transação, sendo que esta classe contém uma propriedade chamada detalhes que informa mais detalhes do erro a partir da documentação.


### 1.2.2) Consultar transação
<pre>
Parametros params = Parametros.of(); //construtor
params.setOperacao(EOperacao.Consulta);
params.setTid("10069930690CDF4F1001");
</pre>

### 1.2.3) Capturar transação
<pre>
Parametros params = Parametros.of(); //construtor
params.setOperacao(EOperacao.Captura);
params.setTid("10069930690CDF4F1001");
params.setValor(1.00); //opcional
params.setCampoLivre("teste de captura"); //opcional
</pre>

### 1.2.4) Cancelar transação
<pre>
Parametros params = Parametros.of(); //construtor
params.setOperacao(EOperacao.Cancelamento);
params.setTid("10069930690CDF4F1001");
</pre>

### 1.2.5) Construtor direto

Existe um construtor de parametros já com a operação e o tid para facilitar as transações que não são de Registro.
<pre>
Parametros Parametros.of(EOperacao operacao, String tid)
</pre>



### 2) Rodando os testes unitários:

Você pode executar os testes unitários da API criando uma suite no jUnit e adicionando a class br.com.flexait.gateway.suites.AllTests e criar o properties (tópico #3) com as configurações do seu ambiente.

Ex.:
<pre>
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import br.com.flexait.gateway.suites.AllTests;

@RunWith(Suite.class)
@SuiteClasses({ AllTests.class })
public class SuaClassTest {
...
}
</pre>



### 3) Configuração da API

Properties:
<pre>
# TESTE | PRODUCAO

gateway.ambiente = TESTE
gateway.modulo = CIELO
gateway.url = https://comercio.locaweb.com.br/comercio.comp

gateway.TESTE.identificador = 1006993069
gateway.PRODUCAO.identificador = xxxxxx //sua chave, pois a chave da locaweb não funciona
</pre>



### 4) Ativando o modo debug

Para verificar o andamento das requisições, basta ativar no log4j o pacote 'br.com.flexait'

Properties:
<pre>
log4j.category.org.apache.http=DEBUG
log4j.category.br.com.flexait=DEBUG
</pre>



### 5) Mock para GatewayService

Existe um GatewayMock para facilitar a implementação dos seus testes
Para isto basta instanciar o GatewayMock ao invés do GatewayService

<pre>
IGatewayService GatewayMock.of();
</pre>
