<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@page import="com.liferay.portal.kernel.captcha.CaptchaTextException"%>
<%@page import="com.liferay.portal.kernel.captcha.CaptchaException"%>
<%@page import="com.liferay.portal.kernel.captcha.CaptchaConfigurationException" %>
<%@page import="java.util.ResourceBundle"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.test.employees.constants.MVCCommandNames"%>
<%@ taglib uri="http://liferay.com/tld/captcha" prefix="liferay-captcha" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-multiselect.css" type="text/css" />