package br.com.flexait.gateway.exception;

import br.com.flexait.gateway.service.GatewayService;


public class GatewayException extends Exception {

	private static final long serialVersionUID = -5834914214079388480L;
	
	public GatewayException(String message, Exception e) {
		super(message, e);
		GatewayService.log.error(e.getMessage());
	}

	public GatewayException(String message) {
		super(message);
		GatewayService.log.error(message);
	}

}
