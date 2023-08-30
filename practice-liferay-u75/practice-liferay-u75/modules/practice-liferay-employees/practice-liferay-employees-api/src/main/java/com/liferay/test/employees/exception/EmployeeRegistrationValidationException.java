package com.liferay.test.employees.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

public class EmployeeRegistrationValidationException extends PortalException {
	public EmployeeRegistrationValidationException() {
	}

	public EmployeeRegistrationValidationException(String msg) {
		super(msg);
	}

	public EmployeeRegistrationValidationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EmployeeRegistrationValidationException(Throwable throwable) {
		super(throwable);
	}
	/**
	 * Custom constructor taking a list as a parameter.
	 *
	 * @param errors
	 */
	public EmployeeRegistrationValidationException(List<String> errors) {
		super(String.join(", ", errors));
		this.errors = errors;
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	private List<String> errors;
}
