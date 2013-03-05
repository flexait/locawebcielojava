package br.com.flexait.gateway.model;

import java.util.Calendar;

import br.com.flexait.gateway.enums.ENivelSeguranca;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

@Data
@XStreamAlias("autenticacao")
public class Autenticacao {
	
	private long codigo;
	
	private String mensagem;
	
	@XStreamAlias("data-hora")
	private Calendar dataHora;
	
	private double valor;
	
	private ENivelSeguranca eci;
	
}
