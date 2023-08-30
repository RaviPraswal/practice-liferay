package com.liferay.test.employees.validator;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = EmployeeFieldsValidator.class)
public class EmployeeFieldsValidator {
	public static boolean isEmployeeRegistrationValid(String firstName, String middleName, String lastName, String email, String phone, String dob, ActionRequest actionRequest) {
		log.info("Employee FName : "+ firstName+"  Employee MName : "+ middleName+"  Employee LName : "+lastName+"  Employee Email : "+ email+"  Employee Phone : "+ phone);
		boolean isValid = true;
		isValid &= validateFirstName(firstName,actionRequest);
		log.error("isFirstNameValid : "+isValid);
		isValid &= validateMiddleName(middleName,actionRequest);
		log.error("isMiddleNameValid : "+isValid);
		isValid &= validateLastName(lastName,actionRequest);
		log.error("isLastNameValid : "+isValid);
		isValid &= validateEmail(email,actionRequest);
		log.error("isEmailValid : "+isValid);
		isValid &= validateDOB(dob,actionRequest);
		log.error("isDOBValid : "+isValid);
		isValid &= validatePhone(phone,actionRequest);
		log.error("isPhoneNumberValid : "+isValid);
		return isValid;
	}

	private static boolean validateDOB(String dob, ActionRequest actionRequest) {
		boolean isValid = true;
		if (Validator.isNull(dob) || dob.trim().isEmpty()) {
			SessionErrors.add(actionRequest, "dob.empty");
			isValid = false;
		} else isValid = true;
		log.info("isValidateDOB: "+isValid);
		return isValid;
	}

	public static boolean validateFirstName(String firstName, ActionRequest actionRequest) {
		boolean isValid = true;
		String firstNameRegex = "([a-zA-z]{1}[a-zA-z_'-,.]{0,13}[a-zA-Z]{0,1})";
		Pattern pattern = Pattern.compile(firstNameRegex);
		Matcher matcher = pattern.matcher(firstName);
		if (Validator.isNull(firstName) || firstName.trim().isEmpty()) {
			SessionErrors.add(actionRequest, "first.name.empty");
			isValid = false;
		} else {
			if (matcher.matches()) {
				isValid = true;
			}else {
				SessionErrors.add(actionRequest, "invalid.first.name");
				isValid = false;
			}
		}
		log.info(isValid);
		return isValid;
	}

	public static boolean validateMiddleName(String middleName, ActionRequest actionRequest) {
		boolean isValid = true;
		String middleNameRegex = "([a-zA-z]{1}[a-zA-z_'-,.]{0,13}[a-zA-Z]{0,1})";
		Pattern pattern = Pattern.compile(middleNameRegex);
		Matcher matcher = pattern.matcher(middleName);
		if (Validator.isNull(middleName) || middleName.trim().isEmpty()) {
			SessionErrors.add(actionRequest, "middle.name.empty");
			isValid = false;
		} else {
			if (matcher.matches())
				isValid = true;
			else {
				SessionErrors.add(actionRequest, "invalid.middle.name");
				isValid = false;
			}
		}
		return isValid;
	}

	public static boolean validateLastName(String lastName, ActionRequest actionRequest) {
		boolean isValid = true;
		String lastNameRegex = "([a-zA-z]{1}[a-zA-z_'-,.]{0,13}[a-zA-Z]{0,1})";
		Pattern pattern = Pattern.compile(lastNameRegex);
		Matcher matcher = pattern.matcher(lastName);
		if (Validator.isNull(lastName) || lastName.trim().isEmpty()) {
			SessionErrors.add(actionRequest, "last.name.empty");
			isValid = false;
		} else {
			if (matcher.matches())
				isValid = true;
			else {
				SessionErrors.add(actionRequest, "invalid.last.name");
				isValid = false;
			}
		}
		return isValid;
	}

	public static boolean validateEmail(String email, ActionRequest actionRequest) {
		boolean isValid = true;
		String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		if (Validator.isNull(email) || email.trim().isEmpty()) {
			SessionErrors.add(actionRequest, "email.address.empty");
			isValid = false;
		} else {
			if (matcher.matches())
				isValid = true;
			else {
				SessionErrors.add(actionRequest, "invalid.email.address");
				isValid = false;
			}
		}
		return isValid;
	}

	public static boolean validatePhone(String phone, ActionRequest actionRequest) {
		boolean isValid = true;
		String phoneRegex = "^[6-9]{1}[0-9]{9}$";
		Pattern pattern = Pattern.compile(phoneRegex);
		Matcher matcher = pattern.matcher(phone);
		if (Validator.isNull(phone) || phone.trim().isEmpty()) {
			SessionErrors.add(actionRequest, "phone.number.empty");
			isValid = false;
		} else {
			if (matcher.matches())
				isValid = true;
			else {
				SessionErrors.add(actionRequest, "invalid.phone.number");
				isValid = false;
			}
		}
		log.info("is phone valid: "+isValid);
		return isValid;
	}
	private static Log log = LogFactoryUtil.getLog(EmployeeFieldsValidator.class.getName());
}
