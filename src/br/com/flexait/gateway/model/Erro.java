package br.com.flexait.gateway.model;

import lombok.Data;
import br.com.flexait.gateway.enums.ETipoErro;
import br.com.flexait.gateway.error.AErro;
import br.com.flexait.gateway.error.Erro001;
import br.com.flexait.gateway.error.Erro002;
import br.com.flexait.gateway.error.Erro003;
import br.com.flexait.gateway.error.Erro010;
import br.com.flexait.gateway.error.Erro011;
import br.com.flexait.gateway.error.Erro012;
import br.com.flexait.gateway.error.Erro020;
import br.com.flexait.gateway.error.Erro021;
import br.com.flexait.gateway.error.Erro022;
import br.com.flexait.gateway.error.Erro030;
import br.com.flexait.gateway.error.Erro031;
import br.com.flexait.gateway.error.Erro032;
import br.com.flexait.gateway.error.Erro033;
import br.com.flexait.gateway.error.Erro040;
import br.com.flexait.gateway.error.Erro041;
import br.com.flexait.gateway.error.Erro042;
import br.com.flexait.gateway.error.Erro099;
import br.com.flexait.gateway.error.Erro999;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Data
@XStreamAlias("erro")
public class Erro {

	private String codigo;
	
	private String mensagem;
	
	@XStreamAlias("codigo_operadora")
	private String codigoOperadora;

	private AErro detalhes;
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
		setDetalhes(codigo);
	}
	
	public AErro getDetalhes() {
		if(detalhes == null) {
			setDetalhes(codigo);
		}
		return detalhes;
	}

	public void setDetalhes(String codigo) {
		ETipoErro tipoErro = ETipoErro.get(codigo);

		switch (tipoErro) {

		case E001:
			detalhes = new Erro001();
			break;

		case E002:
			detalhes = new Erro002();
			break;

		case E003:
			detalhes = new Erro003();
			break;

		case E010:
			detalhes = new Erro010();
			break;

		case E011:
			detalhes = new Erro011();
			break;

		case E012:
			detalhes = new Erro012();
			break;
		
		case E020:
			detalhes = new Erro020();
			break;

		case E021:
			detalhes = new Erro021();
			break;

		case E022:
			detalhes = new Erro022();
			break;

		case E030:
			detalhes = new Erro030();
			break;

		case E031:
			detalhes = new Erro031();
			break;

		case E032:
			detalhes = new Erro032();
			break;

		case E033:
			detalhes = new Erro033();
			break;

		case E040:
			detalhes = new Erro040();
			break;

		case E041:
			detalhes = new Erro041();
			break;

		case E042:
			detalhes = new Erro042();
			break;

		case E099:
			detalhes = new Erro099();
			break;
			
		case E999:
			detalhes = new Erro999();
			break;
			
		case NENHUM:

		default:
			throw new IllegalArgumentException("Tipo de erro inexistente");
		}
	}

}
