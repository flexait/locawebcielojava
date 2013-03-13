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

public class ParametrosValidate {
	
	private Parametros params;
	@Getter(value = AccessLevel.PROTECTED) private Validator validator;
	@Getter private Set<ConstraintViolation<Parametros>> messages;
	
	ParametrosValidate(Parametros params) {
		this.params = params;
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		messages = new HashSet<>();
	}
	
	protected boolean validate(Set<ConstraintViolation<Parametros>> messages) {
		this.messages = messages;
		if(messages.size() == 0) {
			return true;
		}
		GatewayService.log.debug(messages);
		return false;
	}

	public static ParametrosValidate of(Parametros params) {
		return new ParametrosValidate(params);
	}

	public boolean validateTidGroup() {
		Set<ConstraintViolation<Parametros>> msgs = validator.validate(params, DefaultGroup.class, TIdGroup.class);
		return validate(msgs);
	}

	public boolean validateDefaultGroup() {
		Set<ConstraintViolation<Parametros>> msgs = validator.validate(params, DefaultGroup.class);
		return validate(msgs);
	}

	public boolean validateAutorizacaoGroup() {
		Set<ConstraintViolation<Parametros>> msgs = validator.validate(params, DefaultGroup.class, AutorizacaoGroup.class);
		return validate(msgs);
	}

	public boolean validate() {
		if(params.getOperacao() == EOperacao.AutorizacaoDireta) {
			return validateAutorizacaoGroup();
		}
		return validateTidGroup();
	}

	public String getErros() {
		StringBuilder sb = new StringBuilder();
		for(ConstraintViolation<Parametros> m: messages) {
			sb.append(m.getMessage());
			sb.append("; ");
		}
		return sb.toString();
	}
}