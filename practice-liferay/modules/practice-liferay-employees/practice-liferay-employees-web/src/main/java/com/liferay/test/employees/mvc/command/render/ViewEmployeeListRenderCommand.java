package com.liferay.test.employees.mvc.command.render;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
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
		int currentPage = ParamUtil.getInteger(renderRequest, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_CUR);
		int delta = ParamUtil.getInteger(renderRequest, SearchContainer.DEFAULT_DELTA_PARAM, 5);
		int start = ((currentPage > 0) ? (currentPage - 1) : 0) * delta;
		int end = start + delta;
		List<Employee> employees = null;
		if(Validator.isNull(employees)) {
			employees = new ArrayList<>();
		}
		log.info("Start : "+start+" End : "+end+" Delta : "+delta+" Current Page : "+currentPage);
		employees = EmployeeLocalServiceUtil.getEmployees(start, end);
		int employeeListSize = EmployeeLocalServiceUtil.getEmployees(-1, -1).size();// this will be changed according to your filter
		setAttributesInRequest(renderRequest,employees,delta,employeeListSize);
		
		return PracticeLiferayEmployeesWebPortletKeys.RETURN_TO_VIEW_EMPLOYEE_LIST_JSP;
	}

	private void setAttributesInRequest(RenderRequest renderRequest, List<Employee> employees, int delta, int employeeListSize) {
		renderRequest.setAttribute("delta", delta);
		renderRequest.setAttribute("employeeListSize", employeeListSize);
		renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_LIST_IN_RENDER_REQUEST, employees);
	}
	private static final Log log = LogFactoryUtil.getLog(ViewEmployeeListRenderCommand.class.getName());
}
