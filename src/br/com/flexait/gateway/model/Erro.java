package br.com.flexait.gateway.model;

import lombok.Data;
import br.com.flexait.gateway.enums.ETipoErro;
import br.com.flexait.gateway.error.Erro001;
import br.com.flexait.gateway.error.Erro002;
import br.com.flexait.gateway.error.Erro003;
import br.com.flexait.gateway.error.Erro011;
import br.com.flexait.gateway.error.Erro012;
import br.com.flexait.gateway.error.Erro020;
import br.com.flexait.gateway.error.Erro021;
import br.com.flexait.gateway.error.Erro010;
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
import br.com.flexait.gateway.interfaces.IErro;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Data
@XStreamAlias("erro")
public class Erro {

	private String codigo;
	
	private String mensagem;
	
	@XStreamAlias("codigo_operadora")
	private String codigoOperadora;

	private IErro erro;
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
		setErro(codigo);
	}

	public void setErro(String codigo) {
		ETipoErro tipoErro = ETipoErro.get(codigo);

		switch (tipoErro) {

		case E001:
			erro = new Erro001();
			break;

		case E002:
			erro = new Erro002();
			break;

		case E003:
			erro = new Erro003();
			break;

		case E010:
			erro = new Erro010();
			break;

		case E011:
			erro = new Erro011();
			break;

		case E012:
			erro = new Erro012();
			break;
		
		case E020:
			erro = new Erro020();
			break;

		case E021:
			erro = new Erro021();
			break;

		case E022:
			erro = new Erro022();
			break;

		case E030:
			erro = new Erro030();
			break;

		case E031:
			erro = new Erro031();
			break;

		case E032:
			erro = new Erro032();
			break;

		case E033:
			erro = new Erro033();
			break;

		case E040:
			erro = new Erro040();
			break;

		case E041:
			erro = new Erro041();
			break;

		case E042:
			erro = new Erro042();
			break;

		case E099:
			erro = new Erro099();
			break;
			
		case E999:
			erro = new Erro999();
			break;
			
		case NENHUM:

		default:
			throw new IllegalArgumentException("Tipo de erro inexistente");
		}
	}

}
