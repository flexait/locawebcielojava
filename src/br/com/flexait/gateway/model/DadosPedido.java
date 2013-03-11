package br.com.flexait.gateway.model;

import java.util.Calendar;

import lombok.Data;
import br.com.flexait.gateway.enums.EIdioma;
import br.com.flexait.gateway.xml.CalendarConverter;
import br.com.flexait.gateway.xml.DoubleConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@Data
@XStreamAlias("dados-pedido")
public class DadosPedido {
	
	private long numero;
	
	@XStreamConverter(DoubleConverter.class)
	private double valor;
	
	private int moeda;
	
	@XStreamAlias("data-hora") @XStreamConverter(CalendarConverter.class)
	private Calendar dataHora;
	
	private String descricao;
	
	private EIdioma idioma;
	
	public static DadosPedido of() {
		return new DadosPedido();
	}
	
}
