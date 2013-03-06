package br.com.flexait.gateway.model;

import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Data
public class Retorno {
	
	@XStreamAlias("transacao")
	private Transacao transacao;
	private Erro erro;
	
	public static Retorno of(Transacao transacao, Erro erro) {
		Retorno retorno = new Retorno();
		retorno.setTransacao(transacao);
		retorno.setErro(erro);
		return retorno;
	}
	
}
