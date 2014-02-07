package br.com.flexait.gateway.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.base.Strings;

public class ParametrosArray {

	private List<NameValuePair> list;
	private Parametros params;
	
	ParametrosArray(Parametros params) {
		this.params = params;
		list = new ArrayList<NameValuePair>();
		
		createParams();
	}
	
	public void add(String name, int value) {
		if(!Strings.isNullOrEmpty(name) && value > 0) {
			list.add(new BasicNameValuePair(name, String.valueOf(value)));
		}
	}
	
	public void add(String name, double value) {
		if(!Strings.isNullOrEmpty(name) && value > 0.0) {
			list.add(new BasicNameValuePair(name, String.valueOf(value)));
		}	
	}
	
	public void add(String name, Object obj) {
		if(!Strings.isNullOrEmpty(name) && obj != null && !obj.toString().isEmpty()) {
			list.add(new BasicNameValuePair(name, obj.toString()));
		}
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public List<NameValuePair> getParameters() {
		return list;
	}

	public static ParametrosArray of(Parametros parametros) {
		return new ParametrosArray(parametros);
	}

	protected void createParams() {
		if(params == null) {
			return;
		}
		add("identificacao", params.getIdentificacao());
		add("modulo", params.getModulo());
		add("operacao", params.getOperacao());
		add("ambiente", params.getAmbiente());
		add("bin_cartao", params.getBinCartao());
		add("idioma", params.getIdioma());
		add("valor", params.getValorSemFormato());
		add("pedido", params.getPedido());
		add("descricao", params.getDescricao());
		add("bandeira", params.getBandeira());
		add("forma_pagamento", params.getFormaPagamentoString());
		add("parcelas", params.getParcelas());
		add("autorizar", params.getAutorizarString());
		add("capturar", params.isCapturar());
		add("campo_livre", params.getCampoLivre());
		add("nome_portador_cartao", params.getNomePortadorCartao());
		add("numero_cartao", params.getNumeroCartao());
		add("validade_cartao", params.getValidadeCartao());
		add("indicador_cartao", params.getIndicadorCartaoString());
		add("codigo_seguranca_cartao", params.getCodigoSegurancaCartao());
		add("tid", params.getTid());
	}

}
