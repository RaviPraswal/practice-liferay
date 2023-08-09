/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.test.employees.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.test.employees.exception.EmployeeRegistrationValidationException;
import com.liferay.test.employees.model.Employee;
import com.liferay.test.employees.service.base.EmployeeLocalServiceBaseImpl;
import com.liferay.test.employees.validator.EmployeeRegistrationValidator;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.test.employees.model.Employee",
	service = AopService.class
)
public class EmployeeLocalServiceImpl extends EmployeeLocalServiceBaseImpl {
	public Employee registerEmployee(String fName, String mName,String lName, String email, String phone, Date dob) throws EmployeeRegistrationValidationException{
//		employeeRegistrationValidator.validate(fName, mName, lName, email, phone);
		try {
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			long userId = serviceContext.getUserId();
			
			long groupId = serviceContext.getScopeGroupId();
			String userFullName = userLocalService.getUser(userId).getFullName();
			
			Employee employee = employeeLocalService.createEmployee(counterLocalService.increment(Employee.class.getName()));
			employee.setFirstName(fName);
			employee.setMiddleName(mName);
			employee.setLastName(lName);
			employee.setPhone(phone);
			employee.setEmail(email);
			employee.setDob(dob);
			log.info(userFullName);
			
			//adding workflow related fields
			employee.setUserId(userId);
			employee.setUserName(userFullName);
			employee.setGroupId(groupId);
			employee.setStatus(WorkflowConstants.STATUS_PENDING);
			employee.setStatusByUserId(userId);
			employee.setStatusByUserName(userFullName);
			employee.setStatusDate(new Date());
			
			log.info("Updating Employee");
			employee = employeePersistence.update(employee);
			log.info("Registered Employee's Full Name : "+employee.getFirstName()+" "+employee.getMiddleName()+" "+employee.getLastName());
			log.info("Employee register request status: "+WorkflowConstants.getStatusLabel(employee.getStatus()));

			//making employee as asset
			assetEntryLocalService.updateEntry(employee.getUserId(), employee.getGroupId(), Employee.class.getName(), employee.getEmployeeId(), serviceContext.getAssetCategoryIds(), serviceContext.getAssetTagNames());
			log.info(serviceContext.getAssetCategoryIds()+"   "+ serviceContext.getAssetTagNames());
			
			//indexing the employee asset
			Indexer<Employee> indexer = IndexerRegistryUtil.nullSafeGetIndexer(Employee.class);
			indexer.reindex(employee);
			
			//initiating workflow on employee asset
			WorkflowHandlerRegistryUtil.startWorkflowInstance(employee.getCompanyId(), employee.getGroupId(), employee.getUserId(), Employee.class.getName(), employee.getPrimaryKey(), employee, serviceContext);
			
			return employee;
		}catch (Exception e) {
			log.info(e);
		}
		return null;
	}
	
	// Gets internally called as the status is updated in workflow process
	public Employee updateStatus(long userId, long employeeId, int status, ServiceContext serviceContext) throws PortalException {
        User user = userLocalService.getUser(userId);
        Employee employee = employeeLocalService.getEmployee(employeeId);

        employee.setStatus(status);
        employee.setStatusByUserId(userId);
        employee.setStatusByUserName(user.getFullName());
        employee.setStatusDate(new Date());
        log.info("Employee register request status: "+WorkflowConstants.getStatusLabel(employee.getStatus()));
        employee = employeePersistence.update(employee);

        if(status == WorkflowConstants.STATUS_APPROVED) {
            assetEntryLocalService.updateVisible(Employee.class.getName(), employeeId, true);
        }else {
            assetEntryLocalService.updateVisible(Employee.class.getName(), employeeId, false);
        }

        return employee;

    }
	
	@Reference
	private EmployeeRegistrationValidator employeeRegistrationValidator;
	
	private static Log log = LogFactoryUtil.getLog(EmployeeLocalServiceImpl.class.getName());
}