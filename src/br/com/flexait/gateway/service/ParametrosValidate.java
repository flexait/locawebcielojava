package br.com.flexait.gateway.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import lombok.AccessLevel;
import lombok.Getter;

import br.com.flexait.gateway.interfaces.AutorizacaoGroup;
import br.com.flexait.gateway.interfaces.DefaultGroup;
import br.com.flexait.gateway.interfaces.TIdGroup;
import br.com.flexait.gateway.model.Parametros;

public class ParametrosValidate {
	
	private Parametros params;
	@Getter(value = AccessLevel.PROTECTED) private Validator validator;
	@Getter private Set<ConstraintViolation<Parametros>> messages;
	
	ParametrosValidate(Parametros params) {
		this.params = params;
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	protected boolean validate(Set<ConstraintViolation<Parametros>> messages) {
		this.messages = messages;
		if(messages.size() == 0) {
			return true;
		}
		return false;
	}

	public static ParametrosValidate of(Parametros params) {
		return new ParametrosValidate(params);
	}

	public boolean validateTidGroup() {
		return validate(validator.validate(params, DefaultGroup.class, TIdGroup.class));
	}

	public boolean validateDefaultGroup() {
		return validate(validator.validate(params, DefaultGroup.class));
	}

	public boolean validateAutorizacaoGroup() {
		return validate(validator.validate(params, DefaultGroup.class, AutorizacaoGroup.class));
	}
}
