package br.com.flexait.gateway.exception;


public class GatewayException extends Exception {

	private static final long serialVersionUID = -5834914214079388480L;
	
	public GatewayException(String message, Exception e) {
		super(message, e);
	}

	public GatewayException(String message) {
		super(message);
	}

}
