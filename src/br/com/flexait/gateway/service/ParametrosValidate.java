package br.com.flexait.gateway.service;

import lombok.AllArgsConstructor;
import br.com.flexait.gateway.enums.EIdentificadorCartao;
import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.model.Parametros;

import com.google.common.base.Strings;

@AllArgsConstructor(staticName = "of")
public class ParametrosValidate {
	
	private Parametros params;
	
	public void validate() throws GatewayException {
//		if(params == null) {
//			throw new GatewayException("Parametros nulo");
//		}
		
		EOperacao operacao = params.getOperacao();
		
		boolean valido = true;
		
		switch(operacao) {
		
		case Autorizacao:
		case AutorizacaoDireta:
			valido = dadosCartaoValidate();
			
		case Registro:
			if(valido && dadosTransacaoValidate(params)) {
				throw new GatewayException("Dados obrigatórios não informado para operação de + " + operacao);
			}
			break;
			
		case Cancelamento:
		case Captura:
		case Consulta:
			if(Strings.isNullOrEmpty(params.getTid())) {
				throw new GatewayException(String.format("Operação de %s tid obrigatório", operacao));
			}
			break;
		
		}
		
	}

	protected boolean dadosTransacaoValidate(Parametros params) {
		return Strings.isNullOrEmpty(params.getIdentificacao()) ||
		params.getModulo() == null ||
		params.getOperacao() == null ||
		params.getAmbiente()  == null||
		params.getIdioma() == null ||
		params.getPedido() == 0L ||
		Strings.isNullOrEmpty(params.getDescricao()) ||
		params.getBandeira() == null ||
		params.getFormaPagamento() == null ||
		params.getParcelas() == 0 ||
		Strings.isNullOrEmpty(params.getCampoLivre()) ||
		(params.getOperacao() == EOperacao.Registro && 
		Strings.isNullOrEmpty(params.getBinCartao()));
	}

	protected boolean dadosCartaoValidate() {
		if(
				Strings.isNullOrEmpty(params.getNomePortadorCartao()) ||
				numeroValidate(params) ||
				validadeValidate(params) ||
				params.getIndicadorCartao() == null ||
				codigoValidate(params)
		) {
			return false;
		}
		return true;
	}

	private boolean codigoValidate(Parametros params) {
		return params.getIndicadorCartao() == EIdentificadorCartao.Informado &&
			(Strings.isNullOrEmpty(params.getCodigoSegurancaCartao()) ||
			params.getCodigoSegurancaCartao().length() != 3);
	}

	private boolean validadeValidate(Parametros params) {
		return Strings.isNullOrEmpty(params.getValidadeCartao()) ||
				params.getValidadeCartao().length() != 6;
	}

	private boolean numeroValidate(Parametros params) {
		return Strings.isNullOrEmpty(params.getNumeroCartao()) ||
				params.getNumeroCartao().length() != 16 || 
				(!params.getNumeroCartao().substring(0, 1).equals("4") &&
						!params.getNumeroCartao().substring(0, 1).equals("5"));
	}
}
