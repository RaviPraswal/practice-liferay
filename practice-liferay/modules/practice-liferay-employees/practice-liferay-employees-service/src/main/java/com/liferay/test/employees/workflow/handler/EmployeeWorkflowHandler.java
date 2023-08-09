package com.liferay.test.employees.workflow.handler;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.test.employees.model.Employee;
import com.liferay.test.employees.service.EmployeeLocalServiceUtil;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

@Component(
		property = {
				"model.class.name="+"com.liferay.test.employees.model.Employee"
		},
		service = WorkflowHandler.class
)
public class EmployeeWorkflowHandler extends BaseWorkflowHandler<Employee> {

	@Override
	public String getClassName() {
		return Employee.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public Employee updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		long userId = GetterUtil.getLong((String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
        long classPk = GetterUtil.getLong(workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
        ServiceContext serviceContext = (ServiceContext) workflowContext.get(WorkflowConstants.CONTEXT_SERVICE_CONTEXT);
        log.info("UserId: "+userId);
        log.info("Status: "+status);
        return EmployeeLocalServiceUtil.updateStatus(userId, classPk, status, serviceContext);
	}
	private static Log log = LogFactoryUtil.getLog(EmployeeWorkflowHandler.class.getName());
}
