<%@page import="java.util.Random"%>
<%@ include file="init.jsp" %>
<%@ include file="formFieldValidation.jsp"%>

<liferay-ui:success key="registeredSuccessfully" message="employee-registered-successfully" />

<liferay-portlet:actionURL name="<%=MVCCommandNames.EMPLOYEE_REGISTRATION_ACTION_COMMAND%>" var="registerEmployeeURL" />

<portlet:renderURL var="viewEmpRenderURL">
	<portlet:param name="mvcRenderCommandName" value="<%=MVCCommandNames.VIEW_EMPLOYEE_LIST_RENDER_COMMAND %>" />
</portlet:renderURL>

<portlet:resourceURL id="<%=MVCCommandNames.EMPLOYEE_CAPTCHA_RESOURCE_COMMAND%>" var="captchaResourceURL" />

<%-- <portlet:resourceURL var="captchaURL" /> --%>

<div class="container">
	<div class="col-md-12">
		<aui:button class="btn btn-primary" href="<%= viewEmpRenderURL %>" value="View Employee List" id="viewEmpList" />
	</div>
	<div class="form-title p-2 text-center">
		<h1><liferay-ui:message key="employee-registration-title" /></h1>
	</div>
	<div class="form-body">
		<aui:form action="<%=registerEmployeeURL %>" method="post">
			<div class="row">
				<div class="col-md-4">
					<aui:input type="text" name="firstName" label="employee-form-first-name">
						<aui:validator name="custom" errorMessage="Please enter a valid first name">
                           function (val, fieldNode, ruleValue) {
                              var isValid = true;
                              var regex = /[a-zA-Z]/;
                              isValid = regex.test(val);
                              console.log("--------->"+isValid);
                              if(isValid){
                              	return true;
                              }
                              else{
                              	return false;
                              }
                            }
                         </aui:validator>
                         <aui:validator name="required" errorMessage="Please enter your First Name."/>
					</aui:input>
				</div>
				<div class="col-md-4">
					<aui:input type="text" name="middleName" label="employee-form-middle-name" />
				</div>
				<div class="col-md-4">
					<aui:input type="text" name="lastName" label="employee-form-last-name">
						<aui:validator name="required" errorMessage="Please enter your Last Name."/>
					</aui:input>
				</div>
				<div class="col-md-4">
					<aui:input name="phone" type="text" label="employee-form-phone">
						<aui:validator name="required" errorMessage="Please enter your Phone Number."/>
						<aui:validator name="maxLength">10</aui:validator>
						<aui:validator name="number" />
					</aui:input>
				</div>
				<div class="col-md-4">
					<aui:input name="email" label="employee-form-email">
						<aui:validator name="required" errorMessage="Please enter your Email Address."/>
						<aui:validator name="email"/>
					</aui:input>
				</div>
				<div class="col-md-4">
					<aui:input type="Date" name="dob" label="date-of-birth">
						<aui:validator name="required" errorMessage="Please enter your Date of birth."/>
					</aui:input>
				</div>
				<%-- <div class="col-md-12">
					<liferay-captcha:captcha/>
				</div> --%>
				
				
				<div class="col-md-12">
					<div class="my-3 taglib-captcha">
						<img alt="Text to Identify" class="captcha d-inline-block mb-2"
							id="<portlet:namespace />captcha" src="<%= captchaResourceURL %>">
						<span class="align-top d-inline-block refresh lfr-portal-tooltip" title="Refresh CAPTCHA"> <a href="javascript:;"
								target="_self" class=" lfr-icon-item taglib-icon refreshCaptcha captcha-reload"> 
								<span class="c-inner" tabindex="-1">
									<img class="icon-reload" src="<%= request.getContextPath() %>/images/ico_refresh_capcha.svg" alt="reload-captcha" >
								</span> <span class="taglib-text hide-accessible sr-only">Refresh CAPTCHA</span> </a>
						</span>
						<div class="form-group input-text-wrapper">
							<aui:input label="text-verification" name="captchaText" size="10" type="text" value="">
								<aui:validator name="required" />
							</aui:input>
						</div>
					</div>
					<span class="error-text" id="captchaText-error"></span>
				</div>
				
				
				<div class="col-md-12">
					<aui:button type="submit" value="SUBMIT" />
				</div>
			</div>
		</aui:form>
	</div>
</div>
<script>
	$('.alert-dismissible').delay(9000).fadeOut();
	jQuery(".refreshCaptcha").click(function(evt){
	  jQuery(".captcha").attr('src', '<%=captchaResourceURL%>');
	  evt.preventDefault();
	});
	jQuery(".captcha").attr("src", jQuery(".captcha").attr("src")+"&amp;force=" + new Date().getMilliseconds());
</script>