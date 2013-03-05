package br.com.flexait.gateway.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.com.flexait.gateway.enums.EBandeira;
import lombok.Data;

@Data
@XStreamAlias("forma-pagamento")
public class FormaPagamento {
	
	private EBandeira bandeira;
	private long produto;
	private int parcelas;
	
	public static FormaPagamento of() {
		return new FormaPagamento();
	}
	
}
