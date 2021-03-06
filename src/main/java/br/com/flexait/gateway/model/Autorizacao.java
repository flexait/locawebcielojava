package br.com.flexait.gateway.model;

import java.util.Calendar;

import lombok.Data;

import br.com.flexait.gateway.xml.CalendarConverter;
import br.com.flexait.gateway.xml.DoubleConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@Data
@XStreamAlias("autorizacao")
public class Autorizacao {
	
	private long codigo;
	
	private String mensagem;
	
	@XStreamAlias("data-hora") @XStreamConverter(CalendarConverter.class)
	private Calendar dataHora;
	
	@XStreamConverter(DoubleConverter.class)
	private double valor;
	
	private String lr;
	
	private long arp;
	
}
