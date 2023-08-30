package com.liferay.test.employees.service.validator;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.test.employees.exception.EmployeeRegistrationValidationException;
import com.liferay.test.employees.validator.EmployeeRegistrationValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;


@Component(immediate = true, service = EmployeeRegistrationValidator.class)
public class EmployeeRegistrationValidatorImpl implements EmployeeRegistrationValidator {

	private boolean isEmployeeRegistrationValid(String firstName, String middleName, String lastName, String email, String phone, final List<String> errors) {
		boolean isValid = true;
		isValid &= validateFirstName(firstName, errors);
		log.error("isFirstNameValid : "+isValid);
		isValid &= validateMiddleName(middleName, errors);
		log.error("isMiddleNameValid : "+isValid);
		isValid &= validateLastName(lastName, errors);
		log.error("isLastNameValid : "+isValid);
		isValid &= validateEmail(email, errors);
		log.error("isEmailValid : "+isValid);
		isValid &= validatePhone(phone, errors);
		log.error("isPhoneNumberValid : "+isValid);
		return isValid;
	}

	@Override
	public void validate(String firstName, String middleName, String lastName, String email, String phone) throws EmployeeRegistrationValidationException {
		List<String> errors = new ArrayList<String>();

		if (!isEmployeeRegistrationValid(firstName,middleName, lastName, email, phone,  errors)) {
			throw new EmployeeRegistrationValidationException(errors);
		}

	}
	

	public static boolean validateFirstName(String firstName, List<String> errors) {
		boolean isValid = true;
		String firstNameRegex = "([a-zA-z]{1}[a-zA-z_'-,.]{0,13}[a-zA-Z]{0,1})";
		Pattern pattern = Pattern.compile(firstNameRegex);
		Matcher matcher = pattern.matcher(firstName);
		if (Validator.isNull(firstName) || firstName.trim().isEmpty()) {
			errors.add("first.name.empty");
			isValid = false;
		} else {
			if (matcher.matches())
				isValid = true;
			else {
				errors.add("invalid.first.name");
				isValid = false;
			}
		}
		return isValid;
	}

	public static boolean validateMiddleName(String middleName, List<String> errors) {
		boolean isValid = true;
		String middleNameRegex = "([a-zA-z]{1}[a-zA-z_'-,.]{0,13}[a-zA-Z]{0,1})";
		Pattern pattern = Pattern.compile(middleNameRegex);
		Matcher matcher = pattern.matcher(middleName);
		if (Validator.isNull(middleName) || middleName.trim().isEmpty()) {
			errors.add("middle.name.empty");
			isValid = false;
		} else {
			if (matcher.matches())
				isValid = true;
			else {
				errors.add("invalid.middle.name");
				isValid = false;
			}
		}
		return isValid;
	}

	public static boolean validateLastName(String lastName, List<String> errors) {
		boolean isValid = true;
		String lastNameRegex = "([a-zA-z]{1}[a-zA-z_'-,.]{0,13}[a-zA-Z]{0,1})";
		Pattern pattern = Pattern.compile(lastNameRegex);
		Matcher matcher = pattern.matcher(lastName);
		if (Validator.isNull(lastName) || lastName.trim().isEmpty()) {
			errors.add("last.name.empty");
			isValid = false;
		} else {
			if (matcher.matches())
				isValid = true;
			else {
				errors.add("invalid.last.name");
				isValid = false;
			}
		}
		return isValid;
	}

	public static boolean validateEmail(String email, List<String> errors) {
		boolean isValid = true;
		String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		if (Validator.isNull(email) || email.trim().isEmpty()) {
			errors.add("email.address.empty");
			isValid = false;
		} else {
			if (matcher.matches())
				isValid = true;
			else {
				errors.add("invalid.email.address");
				isValid = false;
			}
		}
		return isValid;
	}

	public static boolean validatePhone(String phone, List<String> errors) {
		boolean isValid = true;
		String phoneRegex = "^[6-9]{1}[0-9]{9}$";
		Pattern pattern = Pattern.compile(phoneRegex);
		Matcher matcher = pattern.matcher(phone);
		if (Validator.isNull(phone) || phone.trim().isEmpty()) {
			errors.add("phone.number.empty");
			isValid = false;
		} else {
			if (matcher.matches())
				isValid = true;
			else {
				errors.add("invalid.phone.number");
				isValid = false;
			}
		}
		return isValid;
	}
	private static Log log = LogFactoryUtil.getLog(EmployeeRegistrationValidatorImpl.class.getName());
}
