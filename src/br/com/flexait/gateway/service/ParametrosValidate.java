package br.com.flexait.gateway.service;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import lombok.AccessLevel;
import lombok.Getter;
import br.com.flexait.gateway.enums.EOperacao;
import br.com.flexait.gateway.interfaces.AutorizacaoGroup;
import br.com.flexait.gateway.interfaces.DefaultGroup;
import br.com.flexait.gateway.interfaces.TIdGroup;
import br.com.flexait.gateway.model.Parametros;
import br.com.flexait.gateway.util.PropertiesUtil;

public class ParametrosValidate {
	
	private static final String VALIDATION_MESSAGES = "/ValidationMessages.properties";
	
	private Parametros params;
	@Getter(value = AccessLevel.PROTECTED) private Validator validator;
	@Getter private Set<ConstraintViolation<Parametros>> messages;

	private PropertiesUtil util;
	
	ParametrosValidate(Parametros params) throws Exception {
		this.params = params;
		validator = getValidatorClass();
		messages = new HashSet<>();
		util = PropertiesUtil.of(VALIDATION_MESSAGES);
	}

	private Validator getValidatorClass() throws Exception {
        return Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	protected boolean validate(Set<ConstraintViolation<Parametros>> messages) {
		if(messages.size() == 0) {
			return true;
		}
		GatewayService.log.debug(messages);
		return false;
	}

	public static ParametrosValidate of(Parametros params) throws Exception {
		return new ParametrosValidate(params);
	}

	public boolean validateTidGroup() {
		messages = validator.validate(params, DefaultGroup.class, TIdGroup.class);
		return validate(messages);
	}

	public boolean validateDefaultGroup() {
		messages = validator.validate(params, DefaultGroup.class);
		return validate(messages);
	}

	public boolean validateAutorizacaoGroup() {
		messages = validator.validate(params, DefaultGroup.class, AutorizacaoGroup.class);
		return validate(messages);
	}

	public boolean validate() {
		if(params.getOperacao() == EOperacao.AutorizacaoDireta) {
			return validateAutorizacaoGroup();
		}
		return validateTidGroup();
	}

	public String getErros() throws Exception {
		return getConstraintsMessages();
	}
	
	public String getConstraintsMessages() throws Exception {
		StringBuilder sb = new StringBuilder();
		for(ConstraintViolation<Parametros> m: messages) {
			String message = m.getMessage();
			sb.append(message.replace("{field}", getPropertyPath(m)));
			sb.append(";\r\n ");
		}
		return sb.toString();
	}

	private String getPropertyPath(ConstraintViolation<Parametros> m) throws Exception {
		String propertyPath = m.getPropertyPath().toString();
		propertyPath = util.get(propertyPath);
		if(propertyPath == null) {
			return "";
		}
		return propertyPath;
	}
}