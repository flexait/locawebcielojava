package br.com.flexait.gateway.model;

import java.util.Calendar;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

@Data
@XStreamAlias("autorizacao")
public class Autorizacao {
	
	private long codigo;
	
	private String mensagem;
	
	@XStreamAlias("data-hora")
	private Calendar dataHora;
	
	private double valor;
	
	private String lr;
	
	private long arp;
	
}
