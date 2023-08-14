package com.liferay.test.employees.mvc.command.render;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.test.employees.constants.MVCCommandNames;
import com.liferay.test.employees.constants.PracticeLiferayEmployeesWebPortletKeys;
import com.liferay.test.employees.model.Employee;
import com.liferay.test.employees.service.EmployeeLocalServiceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
		setAttributesInRequest(renderRequest, renderResponse);
		
		return PracticeLiferayEmployeesWebPortletKeys.RETURN_TO_VIEW_EMPLOYEE_LIST_JSP;
	}

	private void setAttributesInRequest(RenderRequest renderRequest, RenderResponse renderResponse) {
		int currentPage = ParamUtil.getInteger(renderRequest, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_CUR);
		int delta = ParamUtil.getInteger(renderRequest, SearchContainer.DEFAULT_DELTA_PARAM, 5);
		int start = ((currentPage > 0) ? (currentPage - 1) : 0) * delta;
		int end = start + delta;
		
		boolean isClearFilter = ParamUtil.getBoolean(renderRequest, "clearFilter");
		long employeeCount = 0l;
		String[] selectedFirstNameArr = null;
		List<Employee> totalEmployees = null;
		Set<String> employeesFirstNameList = EmployeeLocalServiceUtil.getUniqueFirstNames();
		
		if(isClearFilter) {
			renderRequest.getPortletSession().removeAttribute( PracticeLiferayEmployeesWebPortletKeys.SELECTED_FIRST_NAME_IN_RENDER_REQUEST, PortletSession.APPLICATION_SCOPE);
		}else {
			selectedFirstNameArr = ParamUtil.getParameterValues(renderRequest, "listofFirstName");
			log.info("selectedFirstNameArr length : "+selectedFirstNameArr.length);
			renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.SELECTED_FIRST_NAME_IN_RENDER_REQUEST, selectedFirstNameArr);	
			renderRequest.getPortletSession().setAttribute( PracticeLiferayEmployeesWebPortletKeys.SELECTED_FIRST_NAME_IN_RENDER_REQUEST, selectedFirstNameArr, PortletSession.APPLICATION_SCOPE);
		}
		
		totalEmployees = EmployeeLocalServiceUtil.getEmployeeList(selectedFirstNameArr, start, end);
		employeeCount = EmployeeLocalServiceUtil.getEmployeeListCount(selectedFirstNameArr);
		log.info("Start : "+start+" End : "+end+" Delta : "+delta+" Current Page : " +currentPage+" Employee List Size : "+employeeCount);
		renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_LIST_IN_RENDER_REQUEST, totalEmployees);
		renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_LIST_SIZE_IN_RENDER_REQUEST, employeeCount);
		renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.DELTA_IN_RENDER_REQUEST, delta);
		renderRequest.setAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_FIRST_NAME_LIST_IN_RENDER_REQUEST, employeesFirstNameList);
	}
	private static final Log log = LogFactoryUtil.getLog(ViewEmployeeListRenderCommand.class.getName());
}
