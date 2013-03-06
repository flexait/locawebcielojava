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

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.Retorno;
import br.com.flexait.gateway.xml.Parser;

@Data
public class GatewayService {

	public static final String DEFAULT_URL_GATEWAY = "https://comercio.locaweb.com.br/comercio.comp";
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
	
	/**
	 * Construtor para uso em produção, usando url padrão e ambiente de produção
	 * @param identificacao
	 * @param modulo
	 */
	GatewayService(String identificacao, EModulo modulo) {
		this(DEFAULT_URL_GATEWAY, identificacao, modulo, EAmbiente.PRODUCAO);
	}
	
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
	}
	
	/**
	 * Função de construção do service padrão para produção
	 * @param identificacao
	 * @param modulo
	 * @return
	 */
	public static GatewayService of(String identificacao, EModulo modulo) {
		return new GatewayService(identificacao, modulo);
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
	
	protected void setParametros(Parametros parametros) throws GatewayException {
		try {
			this.parametros = parametros;
			this.parametros.setIdentificacao(identificacao);
			this.parametros.setModulo(modulo);
			this.parametros.setAmbiente(ambiente);
			
			List<NameValuePair> httpParameters = this.parametros.getHttpParameters();
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(httpParameters);
			httpPost.setEntity(urlEncodedFormEntity);
			
		} catch (Exception e) {
			throw new GatewayException("Não foi possível encodar os parametros", e);
		}
	}
	
	public void setUrl(String url) {
		this.url = url;
		this.httpPost = new HttpPost(url);
	}

	public Retorno post(Parametros params) throws GatewayException {
		try {
			setParametros(params);
		
			httpClient = configScheme(httpClient);
			
			HttpResponse response = httpClient.execute(httpPost);
			
			InputStream content = response.getEntity().getContent();
			System.out.println(IOUtils.toString(content));
			
			return Parser.of(content).getRetorno();
			
		} catch (Exception e) {
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

	public HttpClient configScheme(HttpClient httpClient) throws Exception {
		Scheme sch = getShema();
		httpClient.getConnectionManager().getSchemeRegistry().register(sch);
		return httpClient;
	}
	
}
