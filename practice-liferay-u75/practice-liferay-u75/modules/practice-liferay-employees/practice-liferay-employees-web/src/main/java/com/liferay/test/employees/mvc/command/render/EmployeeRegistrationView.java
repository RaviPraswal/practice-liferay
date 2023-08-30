package com.liferay.test.employees.mvc.command.render;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.test.employees.constants.*;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
			"javax.portlet.name="+PracticeLiferayEmployeesWebPortletKeys.PRACTICELIFERAYEMPLOYEESWEB,
			"mvc.command.name="+MVCCommandNames.DEFAULT_COMMAND
	},
	service = MVCRenderCommand.class
)
public class EmployeeRegistrationView implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		return PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_REGISTRATION_VIEW_PATH;
	}

}
