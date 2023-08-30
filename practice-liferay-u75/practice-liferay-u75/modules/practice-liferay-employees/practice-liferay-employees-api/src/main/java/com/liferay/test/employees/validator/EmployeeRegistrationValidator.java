package com.liferay.test.employees.validator;

import com.liferay.test.employees.exception.EmployeeRegistrationValidationException;

public interface EmployeeRegistrationValidator {
	public void validate(String firstName, String middleName, String lastName, String email, String phone) throws EmployeeRegistrationValidationException;
}
