package br.com.flexait.gateway.interfaces;

import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.model.Retorno;

public interface IGatewayService {

	Retorno autorizacaoDireta(Parametros params) throws Exception;

	Retorno consultar(Parametros params) throws Exception;

	Retorno capturar(Parametros params) throws Exception;

	Retorno cancelar(Parametros params) throws Exception;

	Retorno registro(Parametros params) throws Exception;

}
