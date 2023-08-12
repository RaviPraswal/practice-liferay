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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
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
		log.debug("-------inside render command-----------");
		int currentPage = ParamUtil.getInteger(renderRequest, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_CUR);
		int delta = ParamUtil.getInteger(renderRequest, SearchContainer.DEFAULT_DELTA_PARAM, 5);
		int start = ((currentPage > 0) ? (currentPage - 1) : 0) * delta;
		int end = start + delta;
		
		//On initial land
//		List<String> firstNameList = new ArrayList();
		Set<String> firstNameSet = new HashSet<>();
		List<Employee> completeEmployeeList = EmployeeLocalServiceUtil.getEmployees(-1, -1);// this will be changed according to your filter
		for (Employee employee : completeEmployeeList) {
			firstNameSet.add(employee.getFirstName());
		}
		int employeeListSize = completeEmployeeList.size();
		
		//When applying pagination
		List<Employee> employees = null;
		if(Validator.isNull(employees)) {
			employees = new ArrayList<>();
		}
		log.info("Start : "+start+" End : "+end+" Delta : "+delta+" Current Page : "+currentPage);
		employees = EmployeeLocalServiceUtil.getEmployees(start, end);
		
		String[] selectedFirstName = ParamUtil.getParameterValues(renderRequest, "listofFirstName");
//		String selectedFirstName = ParamUtil.getString(renderRequest, "listofFirstName");
		String searchTerm = ParamUtil.getString(renderRequest, "searchBar");
		log.info("===searchTerm==="+searchTerm);
		log.info("Selected First Name Arr Length: "+selectedFirstName.length);
		
		
		
		setAttributesInRequest(renderRequest,employees,delta,employeeListSize,firstNameSet,selectedFirstName);
		
		return PracticeLiferayEmployeesWebPortletKeys.RETURN_TO_VIEW_EMPLOYEE_LIST_JSP;
	}

	private void setAttributesInRequest(RenderRequest renderRequest, List<Employee> employees, int delta, int employeeListSize, Set<String> firstNameSet, String[] selectedFirstName) {
		renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.DELTA_IN_RENDER_REQUEST, delta);
		renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_LIST_SIZE_IN_RENDER_REQUEST, employeeListSize);
		renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_FIRST_NAME_LIST_IN_RENDER_REQUEST, firstNameSet);
		renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_LIST_IN_RENDER_REQUEST, employees);
		renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.SELECTED_FIRST_NAME_IN_RENDER_REQUEST, selectedFirstName);
		renderRequest.getPortletSession().setAttribute(PracticeLiferayEmployeesWebPortletKeys.SELECTED_FIRST_NAME_IN_RENDER_REQUEST, selectedFirstName, PortletSession.APPLICATION_SCOPE);
	}
	private static final Log log = LogFactoryUtil.getLog(ViewEmployeeListRenderCommand.class.getName());
}
