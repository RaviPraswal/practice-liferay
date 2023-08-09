package com.liferay.test.employees.mvc.command.render;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.test.employees.constants.MVCCommandNames;
import com.liferay.test.employees.constants.PracticeLiferayEmployeesWebPortletKeys;
import com.liferay.test.employees.model.Employee;
import com.liferay.test.employees.service.EmployeeLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;


@Component(
		property = {
				"mvc.command.name="+MVCCommandNames.VIEW_EMPLOYEE_LIST_RENDER_COMMAND,
				"javax.portlet.name="+PracticeLiferayEmployeesWebPortletKeys.PRACTICELIFERAYEMPLOYEESWEB
		},
		service = MVCRenderCommand.class
)
public class ViewEmployeeListRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {

		List<Employee> employees = null;
		if(Validator.isNull(employees)) {
			employees = new ArrayList<>();
		}
		employees = EmployeeLocalServiceUtil.getEmployees(-1, -1);
		setAttributesInRequest(renderRequest,employees);
		
		return PracticeLiferayEmployeesWebPortletKeys.RETURN_TO_VIEW_EMPLOYEE_LIST_JSP;
	}

	private void setAttributesInRequest(RenderRequest renderRequest, List<Employee> employees) {
		renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_LIST_IN_RENDER_REQUEST, employees);
	}

}
