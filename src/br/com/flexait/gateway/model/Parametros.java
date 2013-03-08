package br.com.flexait.gateway.model;

import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

import org.apache.http.NameValuePair;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.flexait.gateway.enums.EAmbiente;
import br.com.flexait.gateway.enums.EAutorizar;
import br.com.flexait.gateway.enums.EBandeira;
import br.com.flexait.gateway.enums.EFormaPagamento;
import br.com.flexait.gateway.enums.EIndicadorCartao;
import br.com.flexait.gateway.enums.EIdioma;
import br.com.flexait.gateway.enums.EModulo;
import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.interfaces.AutorizacaoGroup;
import br.com.flexait.gateway.interfaces.DefaultGroup;
import br.com.flexait.gateway.interfaces.TIdGroup;

import com.google.common.base.Strings;

@Data
public class Parametros {
	
	protected static final String PATTERN_CODIGO_SEGURANCA = "^[0-9]{3}$";
	
	/** codigo cliente */
	@NotEmpty(groups = DefaultGroup.class)
	private String identificacao; //codigo cliente do gateway
	
	@NotNull(groups = DefaultGroup.class)
	private EModulo modulo;
	
	@NotNull(groups = DefaultGroup.class)
	private EAmbiente ambiente;
	
	@NotNull(groups = DefaultGroup.class)
	private EOperacao operacao;
	
	@Size(max = 6, min = 6)
	private String binCartao;
	
	private EIdioma idioma;
	
	@Min(value = 1, groups = AutorizacaoGroup.class) 
	@NotNull(groups = AutorizacaoGroup.class)
	private Double valor; //dados do pedido
	
	@Min(value = 1L, groups = AutorizacaoGroup.class)
	@NotNull(groups = AutorizacaoGroup.class)
	private Long pedido; //numero do pedido
	
	private String descricao;
	
	@NotNull(groups = AutorizacaoGroup.class)
	private EBandeira bandeira;
	
	@NotNull(groups = AutorizacaoGroup.class) 
	private EFormaPagamento formaPagamento;
	
	@Min(value = 1, groups = AutorizacaoGroup.class)
	@NotNull(groups = AutorizacaoGroup.class)
	private Integer parcelas;
	
	private EAutorizar autorizar;
	
	private boolean capturar;
	
	private String campoLivre;
	
	@NotEmpty(groups = AutorizacaoGroup.class)
	private String nomePortadorCartao;
	
	@Size(max = 16, min = 16, groups = AutorizacaoGroup.class) 
	@NotEmpty(groups = AutorizacaoGroup.class) 
	private String numeroCartao;
	
	@Size(max = 6, min = 6, groups = AutorizacaoGroup.class) 
	@NotEmpty(groups = AutorizacaoGroup.class)
	private String validadeCartao;
	
	@NotNull(groups = AutorizacaoGroup.class)
	private EIndicadorCartao indicadorCartao;
	
	@Size(max = 3, min = 3, groups = AutorizacaoGroup.class)
	@Pattern(regexp = PATTERN_CODIGO_SEGURANCA) 
	@NotEmpty(groups = AutorizacaoGroup.class)
	private String codigoSegurancaCartao;
	
	@NotEmpty(groups = TIdGroup.class)
	private String tid;
	
	@AssertTrue(message = "O código é obrigatório se o indicador for informado", groups = AutorizacaoGroup.class)
	public boolean isCodigoValid() {
		return (indicadorCartao != EIndicadorCartao.Informado && bandeira == EBandeira.visa) ||
				!Strings.isNullOrEmpty(codigoSegurancaCartao);
	}
	
	public boolean isCartaoValid() {
		return false;
	}
	
	public static Parametros of() {
		return new Parametros();
	}
	
	public static Parametros of(EOperacao operacao, String tid) {
		Parametros parametros = new Parametros();
		parametros.setOperacao(operacao);
		parametros.setTid(tid);
		return parametros;
	}

	public List<NameValuePair> getHttpParameters() throws GatewayException {
		return ParametrosArray.of(this).getParameters();
	}

	public String getFormaPagamentoString() {
		if(formaPagamento == null) {
			return null;
		}
		return formaPagamento.getValor();
	}

	public String getAutorizarString() {
		if(autorizar == null) {
			return null;
		}
		return autorizar.getValor();
	}

	public String getIndicadorCartaoString() {
		if(indicadorCartao == null) {
			return null;
		}
		return indicadorCartao.getValor();
	}
	
}
