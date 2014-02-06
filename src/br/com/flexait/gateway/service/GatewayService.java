package br.com.flexait.gateway.service;

import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.interfaces.IGatewayService;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.util.PropertiesUtil;
import br.com.flexait.gateway.xml.Parser;

import com.thoughtworks.xstream.converters.ConversionException;

@Data
public class GatewayService implements IGatewayService {

	public static final String DEFAULT_URL_GATEWAY = "https://comercio.locaweb.com.br/comercio.comp";
	
	public static Logger log = Logger.getLogger(GatewayService.class);
	
	private String url;
	private final String identificacao;
	private final EModulo modulo;
	private final EAmbiente ambiente;
	private Parametros parametros;
	private Retorno retorno;
	@Getter(value = AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED)
	private HttpPost httpPost;
	@Getter(value = AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED)
	private HttpClient httpClient;
	private ParametrosValidate validator;
	private PropertiesUtil util;
	
	/**
	 * Construtor principal
	 * @param url
	 * @param identificacao
	 * @param modulo
	 * @param ambiente
	 */
	GatewayService(String url, String identificacao, EModulo modulo,
			EAmbiente ambiente) {
		super();
		this.httpClient = new DefaultHttpClient();
		
		this.setUrl(url);
		this.identificacao = identificacao;
		this.modulo = modulo;
		this.ambiente = ambiente;
		
		if(log.isDebugEnabled())
			logConstruct(url, identificacao, ambiente);
	}

	private void logConstruct(String url, String identificacao, EAmbiente ambiente) {
		StringBuilder sb = new StringBuilder();
		sb.append("Service instanciado:");
		sb.append("\n\tURL = " + url);
		sb.append("\n\tIdentificacao = " + identificacao);
		sb.append("\n\tAmbiente = " + ambiente);
		log.debug("=====================================================================================================\n");
		log.debug(sb.toString());
	}
	
	/**
	 * Função de construção do service padrão, este é o construtor principal, consumido pelos outros
	 * @param url
	 * @param identificacao
	 * @param modulo
	 * @param ambiente
	 * @return
	 */
	public static GatewayService of(String url, String identificacao, EModulo modulo,
			EAmbiente ambiente) {
		return new GatewayService(url, identificacao, modulo, ambiente);
	}
	
	/**
	 * Construtor vazio, baseado nos valores do properties
	 * @return
	 * @throws Exception
	 */
	public static GatewayService of() throws Exception {
		PropertiesUtil util = getPropertiesUtil();
		return new GatewayService(
			util.getUrl(), util.getIdentificador(), util.getModulo(), util.getAmbiente()
		);
	}
	
	protected static PropertiesUtil getPropertiesUtil() throws Exception {
		return PropertiesUtil.of();
	}
	
	protected void setParametros(Parametros parametros) throws GatewayException {
		try {
			this.parametros = parametros;
			this.parametros.setIdentificacao(identificacao);
			this.parametros.setModulo(modulo);
			this.parametros.setAmbiente(ambiente);
			
			List<NameValuePair> httpParameters = this.parametros.getHttpParameters();
			
			UrlEncodedFormEntity urlEncoded = new UrlEncodedFormEntity(httpParameters);
			httpPost.setEntity(urlEncoded);
			
		} catch (Exception e) {
			throw new GatewayException("Não foi possível encodar os parametros", e);
		}
	}
	
	public void setUrl(String url) {
		this.url = url;
		this.httpPost = new HttpPost(url);
	}

	protected Retorno post(Parametros params) throws GatewayException {
		try {
			setParametros(params);
			
			boolean isValid = validateParams(params);
			if(!isValid) {
				String erros = "Erros de validação: " + validator.getErros();
				log.debug(erros);
				throw new GatewayException(erros, new IllegalArgumentException(erros));
			}
		
			httpClient = configScheme(httpClient);
			
			if(log.isDebugEnabled())
				log.debug("Post com os parametros:\n\t" + IOUtils.toString(httpPost.getEntity().getContent()));
			
			HttpResponse response = httpClient.execute(httpPost);
			
			InputStream content = response.getEntity().getContent();
			
			Retorno retorno = Parser.of(content).getRetorno();
			
			retorno.setOperacao(params.getOperacao());
			
			return retorno;
			
		} catch (ConversionException e) {
			log.error(e);
			throw new GatewayException("Ocorreu um erro ao executar o post: " + e.getMessage());
		} 
		catch (Exception e) {
			log.error(e);
			throw new GatewayException("Ocorreu um problema ao executar o post", e);
		}
	}

	protected static Scheme getShema() throws NoSuchAlgorithmException,
			KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(
			null, 
			new TrustManager[] {
				new X509TrustManager() {
					
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					
					public void checkServerTrusted(X509Certificate[] arg0, String arg1)
							throws CertificateException {
					}
					
					public void checkClientTrusted(X509Certificate[] arg0, String arg1)
							throws CertificateException {
					}
				}
			},
			new SecureRandom()
		);
		
		SSLSocketFactory factory = new SSLSocketFactory(sslContext);
		Scheme sch = new Scheme("https", 443, factory);
		return sch;
	}

	protected HttpClient configScheme(HttpClient httpClient) throws Exception {
		Scheme sch = getShema();
		httpClient.getConnectionManager().getSchemeRegistry().register(sch);
		return httpClient;
	}

	protected boolean validateParams(Parametros params) throws Exception {
		validator = ParametrosValidate.of(params);
		return validator.validate();
	}

	public Retorno autorizacaoDireta(Parametros params) throws GatewayException {
		params.setOperacao(EOperacao.AutorizacaoDireta);
		return post(params);
	}

	public Retorno consultar(Parametros params) throws GatewayException {
		params.setOperacao(EOperacao.Consulta);
		return post(params);
	}

	public Retorno capturar(Parametros params) throws GatewayException {
		params.setOperacao(EOperacao.Captura);
		return post(params);
	}

	public Retorno cancelar(Parametros params) throws GatewayException {
		params.setOperacao(EOperacao.Cancelamento);
		return post(params);
	}

}
