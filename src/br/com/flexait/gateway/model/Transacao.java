package br.com.flexait.gateway.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.com.flexait.gateway.enums.EStatus;
import lombok.Data;

@Data
@XStreamAlias("transacao")
public class Transacao {
	
	private String tid; //id da transação
	
	private String pam;
	
	private EStatus status;
	
	@XStreamAlias("url-autenticacao")
	private String urlAutenticacao;
	
	@XStreamAlias("dados-pedido")
	private DadosPedido dadosPedido;
	
	@XStreamAlias("forma-pagamento")
	private FormaPagamento formaPagamento;
	
	private Autenticacao autenticacao;
	
	private Autorizacao autorizacao;
	
	private Captura captura;
	
	private Cancelamento cancelamento;	
	
	public static Transacao of() {
		return new Transacao();
	}
	
}
