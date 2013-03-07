package br.com.flexait.gateway.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.enums.EAutorizar;
import br.com.flexait.gateway.enums.EBandeira;
import br.com.flexait.gateway.enums.EFormaPagamento;
import br.com.flexait.gateway.enums.EIdentificadorCartao;
import br.com.flexait.gateway.enums.EIdioma;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.util.NumberUtil;

@Data
public class Parametros {
	
	/** codigo cliente */
	private String identificacao; //codigo cliente do gateway
	private EModulo modulo;
	private EAmbiente ambiente;
	private EOperacao operacao;
	private String binCartao;
	private EIdioma idioma;
	
	//dados do pedido
	private double valor;
	private long pedido; //numero do pedido
	private String descricao;
	private EBandeira bandeira;
	private EFormaPagamento formaPagamento;
	private int parcelas;
	private EAutorizar autorizar;
	private boolean capturar;
	private String campoLivre;
	
	private String nomePortadorCartao;
	private String numeroCartao;
	private String validadeCartao;
	private EIdentificadorCartao indicadorCartao;
	private String codigoSegurancaCartao;
	
	private String tid;
	
	
	public static Parametros of() {
		return new Parametros();
	}
	
	public static Parametros of(EOperacao operacao, String tid) {
		Parametros parametros = new Parametros();
		parametros.setOperacao(operacao);
		parametros.setTid(tid);
		return parametros;
	}

	public List<NameValuePair> getHttpParameters() throws GatewayException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		if(identificacao != null) {
			params.add(new BasicNameValuePair("identificacao", identificacao));
		}
		
		if(modulo != null) {
			params.add(new BasicNameValuePair("modulo", modulo.toString()));
		}
		
		if(operacao != null) {
			params.add(new BasicNameValuePair("operacao", operacao.toString()));
		}
		
		if(ambiente != null) {
			params.add(new BasicNameValuePair("ambiente", ambiente.toString()));
		}
		
		if(binCartao != null) {
			params.add(new BasicNameValuePair("bin_cartao", binCartao));
		}
		
		if(idioma != null) {
			params.add(new BasicNameValuePair("idioma", idioma.toString()));
		}
		
		if(valor > 0.0) {
			params.add(new BasicNameValuePair("valor", NumberUtil.format(valor)));
		}
		
		if(pedido > 0L) {
			params.add(new BasicNameValuePair("pedido", String.valueOf(pedido)));
		}
		
		if(descricao != null) {
			params.add(new BasicNameValuePair("descricao", descricao));
		}
		
		if(bandeira != null) {
			params.add(new BasicNameValuePair("bandeira", bandeira.toString()));
		}
		
		if(formaPagamento != null) {
			params.add(new BasicNameValuePair("forma_pagamento", formaPagamento.getValor()));
		}
		
		if(parcelas > 0) {
			params.add(new BasicNameValuePair("parcelas", String.valueOf(parcelas)));
		}
		
		if(autorizar != null) {
			params.add(new BasicNameValuePair("autorizar", autorizar.getValor()));
		}
		
		params.add(new BasicNameValuePair("capturar", String.valueOf(capturar)));
		
		if(campoLivre != null) {
			params.add(new BasicNameValuePair("campo_livre", campoLivre));
		}
		
		if(nomePortadorCartao != null) {
			params.add(new BasicNameValuePair("nome_portador_cartao", nomePortadorCartao));
		}
		
		if(numeroCartao != null) {
			params.add(new BasicNameValuePair("numero_cartao", numeroCartao));
		}
		
		if(validadeCartao != null) {
			params.add(new BasicNameValuePair("validade_cartao", validadeCartao));
		}
		
		if(indicadorCartao != null) {
			params.add(new BasicNameValuePair("indicador_cartao", indicadorCartao.getValor()));
		}
		
		if(codigoSegurancaCartao != null) {
			params.add(new BasicNameValuePair("codigo_seguranca_cartao", codigoSegurancaCartao));
		}
		
		if(tid != null) {
			params.add(new BasicNameValuePair("tid", tid));
		}
		
		return params;
	}
	
}
