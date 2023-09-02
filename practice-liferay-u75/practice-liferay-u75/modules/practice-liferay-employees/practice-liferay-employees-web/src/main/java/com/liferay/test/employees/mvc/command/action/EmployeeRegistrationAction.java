package com.liferay.test.employees.mvc.command.action;

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.test.employees.constants.MVCCommandNames;
import com.liferay.test.employees.constants.PracticeLiferayEmployeesWebPortletKeys;
import com.liferay.test.employees.model.Employee;
import com.liferay.test.employees.service.EmployeeLocalService;
import com.liferay.test.employees.util.DateUtil;
import com.liferay.test.employees.validator.EmployeeFieldsValidator;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = { "javax.portlet.name=" + PracticeLiferayEmployeesWebPortletKeys.PRACTICELIFERAYEMPLOYEESWEB,
		"mvc.command.name=" + MVCCommandNames.EMPLOYEE_REGISTRATION_ACTION_COMMAND }, service = MVCActionCommand.class)
public class EmployeeRegistrationAction extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws ParseException, Exception {
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		String email = ParamUtil.getString(actionRequest, "email");
		String phone = ParamUtil.getString(actionRequest, "phone");
		String dob = ParamUtil.getString(actionRequest, "dob");

		boolean areValid = false;

		try {
			log.info("Validating Captcha");
			//If captcha is not validated then this method will throw CaptchaTextException 
			//so the rest of the lines will be skipped and jumped to respective catch block
			CaptchaUtil.check(actionRequest);
			log.info("CAPTCHA verification successful.");
			areValid = validateFieldParams(firstName, middleName, lastName, email, phone, dob, actionRequest);
			if (areValid) {
				Employee registeredEmployee = null;
				log.info(dob);
				Date formattedDOB = new Date();
				try {
					formattedDOB = (Date) DateUtil.dateFormater(dob, "yyyy/MM/dd", Date.class.getName());
					log.info("formattedDOB : " + formattedDOB);
				} catch (ClassNotFoundException | java.text.ParseException e) {
					log.error(e);
				}
				log.info(formattedDOB);
				registeredEmployee = employeeLocalService.registerEmployee(firstName, middleName, lastName, email,
						phone, formattedDOB);
				log.info("Registered Employee's Full Name : " + registeredEmployee.getFirstName() + " "
						+ registeredEmployee.getMiddleName() + " " + registeredEmployee.getLastName());
			}

		} catch (Exception exception) {
			if (exception instanceof CaptchaTextException) {
				SessionErrors.add(actionRequest, exception.getClass(), exception);
				log.error("CAPTCHA verification failed.");
				return;
			}
		}

	}

	private boolean validateFieldParams(String firstName, String middleName, String lastName, String email,
			String phone, String dob, ActionRequest actionRequest) {
		return EmployeeFieldsValidator.isEmployeeRegistrationValid(firstName, middleName, lastName, email, phone, dob,
				actionRequest);
	}

	private static Log log = LogFactoryUtil.getLog(EmployeeRegistrationAction.class.getName());


	@Reference
	EmployeeLocalService employeeLocalService;
}
