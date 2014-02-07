package br.com.flexait.gateway.model;

import lombok.Data;
import br.com.flexait.gateway.enums.EStatus;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Data
@XStreamAlias("transacao")
public class Transacao {
	
	private String tid; //id da transação
	
	private String pan;
	
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
