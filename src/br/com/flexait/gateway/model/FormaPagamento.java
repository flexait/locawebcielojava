package br.com.flexait.gateway.model;

import lombok.Data;
import br.com.flexait.gateway.enums.EBandeira;

import com.thoughtworks.xstream.annotations.XStreamAlias;

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
