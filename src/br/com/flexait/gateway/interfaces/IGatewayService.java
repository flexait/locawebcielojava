package br.com.flexait.gateway.interfaces;

import br.com.flexait.gateway.exception.GatewayException;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.Retorno;

public interface IGatewayService {

	Retorno post(Parametros params) throws GatewayException;

}
