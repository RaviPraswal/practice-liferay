  <%@page import="java.util.ResourceBundle"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@ include file="init.jsp" %>
    
<%-- For Error And Related Support --%>
<liferay-ui:error key="serviceErrorDetails">
	<liferay-ui:message key="error.registration-service-error" arguments='<%= SessionErrors.get(liferayPortletRequest, "serviceErrorDetails") %>' />
</liferay-ui:error>

<liferay-ui:error key="first.name.empty" message="<%=ResourceBundle.getBundle("/content/Error-Messages").getString("first.name.empty")%>" />
<liferay-ui:error key="invalid.first.name" message="<%=ResourceBundle.getBundle("/content/Error-Messages").getString("invalid.first.name")%>" />

<liferay-ui:error key="middle.name.empty" message="<%=ResourceBundle.getBundle("/content/Error-Messages").getString("middle.name.empty")%>"/>
<liferay-ui:error key="invalid.middle.name" message="<%=ResourceBundle.getBundle("/content/Error-Messages").getString("invalid.middle.name")%>"/>

<liferay-ui:error key="last.name.empty" message="<%=ResourceBundle.getBundle("/content/Error-Messages").getString("last.name.empty")%>"/>
<liferay-ui:error key="invalid.last.name" message="<%=ResourceBundle.getBundle("/content/Error-Messages").getString("invalid.last.name")%>" />

<liferay-ui:error key="email.address.empty" message="<%=ResourceBundle.getBundle("/content/Error-Messages").getString("email.address.empty")%>"/>
<liferay-ui:error key="invalid.email.address" message="<%=ResourceBundle.getBundle("/content/Error-Messages").getString("invalid.email.address")%>"/>

<liferay-ui:error key="phone.number.empty" message="<%=ResourceBundle.getBundle("/content/Error-Messages").getString("phone.number.empty")%>" />
<liferay-ui:error key="invalid.phone.number" message="<%=ResourceBundle.getBundle("/content/Error-Messages").getString("invalid.phone.number")%>" />

<liferay-ui:error key="dob.empty" message="<%=ResourceBundle.getBundle("/content/Error-Messages").getString("dob.empty")%>" />