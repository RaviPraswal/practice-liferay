package com.liferay.test.employees.mvc.command.resource;

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.test.employees.constants.MVCCommandNames;
import com.liferay.test.employees.constants.PracticeLiferayEmployeesWebPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		property = {
			"javax.portlet.name="+PracticeLiferayEmployeesWebPortletKeys.PRACTICELIFERAYEMPLOYEESWEB,
			"mvc.command.name="+MVCCommandNames.EMPLOYEE_CAPTCHA_RESOURCE_COMMAND
		},
		 service = MVCResourceCommand.class
	)
public class EmployeeCaptchaResourceCommand extends BaseMVCResourceCommand  {

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		log.info("inside captcha resource command");
		try {
//			HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(resourceRequest);
//			HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(resourceResponse);
            CaptchaUtil.serveImage(resourceRequest, resourceResponse);
        }catch(Exception exception) {
            log.error(exception.getMessage(), exception);
        }
		
	}
	private static final Log log = LogFactoryUtil.getLog(EmployeeCaptchaResourceCommand.class.getName());
}
