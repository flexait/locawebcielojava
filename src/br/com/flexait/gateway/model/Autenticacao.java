package br.com.flexait.gateway.model;

import java.util.Calendar;

import lombok.Data;
import br.com.flexait.gateway.enums.ENivelSeguranca;
import br.com.flexait.gateway.xml.CalendarConverter;
import br.com.flexait.gateway.xml.DoubleConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@Data
@XStreamAlias("autenticacao")
public class Autenticacao {
	
	private long codigo;
	
	private String mensagem;
	
	@XStreamAlias("data-hora") @XStreamConverter(CalendarConverter.class)
	private Calendar dataHora;
	
	@XStreamConverter(DoubleConverter.class)
	private double valor;
	
	private ENivelSeguranca eci;
	
}
