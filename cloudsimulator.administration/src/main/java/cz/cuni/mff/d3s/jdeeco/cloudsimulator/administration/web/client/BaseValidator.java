package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import java.lang.reflect.ParameterizedType;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.StringEx;

public abstract class BaseValidator<T> implements Validator {

	public static final String NAME_FIELD = "name";
	public static final String DESCRIPTION_FIELD = "description";
	
	protected static final String NOT_SPECIFIED_ERROR = "error.empty";
	protected static final String INVALID_VALUE_ERROR = "error.invalidValue";


	protected static final String NULL_MESSAGE = "Nothing is specified.";
	protected static final String INVALID_VALUE_MESSAGE_TEMPLATE = "'%s' has invalid value. Correct value: '%s'.";
	protected static final String NOT_SPECIFIED_MESSAGE_TEMPLATE = "'%s' is not specified.";
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		@SuppressWarnings("unchecked")
		Class<T> thisClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass())
				      .getActualTypeArguments()[0];
		return thisClass.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target == null) {
			errors.reject("error.empty", NULL_MESSAGE);
		} else {
			@SuppressWarnings("unchecked")
			T item = (T) target;
			validateInternal(item, errors);
		}
	}

	protected abstract void validateInternal(T item, Errors errors);

	protected boolean checkNotSpecified(String fieldValue) {
		return StringEx.isNullOrEmpty(fieldValue);
	}

	protected void addNotSpecifiedError(Errors errors, String fieldId) {
		String fieldName = StringEx.firstToUpper(fieldId);
		addNotSpecifiedError(errors, fieldId, fieldName);
	}
	
	protected void addNotSpecifiedError(Errors errors, String fieldId, String fieldName) {
		errors.rejectValue(fieldId, NOT_SPECIFIED_ERROR, String.format(NOT_SPECIFIED_MESSAGE_TEMPLATE, fieldName));
	}

	protected void addInvalidValueError(Errors errors, String fieldId, String correctValueDef) {
		String fieldName = StringEx.firstToUpper(fieldId);
		addInvalidValueError(errors, fieldId, fieldName, correctValueDef);
	}
	
	protected void addInvalidValueError(Errors errors, String fieldId, String fieldName, String correctValueDef) {
		errors.rejectValue(fieldId, INVALID_VALUE_ERROR, String.format(INVALID_VALUE_MESSAGE_TEMPLATE, fieldName, correctValueDef));
	}
}
