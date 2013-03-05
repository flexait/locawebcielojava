package br.com.flexait.gateway.model;

import java.util.Calendar;

import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Data
@XStreamAlias("cancelamento")
public class Cancelamento {

	private long codigo;
	
	private String mensagem;
	
	@XStreamAlias("data-hora")
	private Calendar dataHora;
	
	private double valor;
	
}
