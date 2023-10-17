<%@page import="java.util.Random"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ include file="init.jsp" %>
<%@ include file="formFieldValidation.jsp"%>

<liferay-ui:success key="registeredSuccessfully" message="employee-registered-successfully" />
<%-- <portlet:resourceURL id="<%=MVCCommandNames.EMPLOYEE_CAPTCHA_RESOURCE_COMMAND%>" var="captchaResourceURL" /> --%>
<liferay-portlet:actionURL name="<%=MVCCommandNames.EMPLOYEE_REGISTRATION_ACTION_COMMAND%>" var="registerEmployeeURL" />
<portlet:actionURL var="getAudioCaptchaURL" name="<%=MVCCommandNames.AUDIO_CAPTCHA_ACTION_COMMAND%>" windowState="<%=LiferayWindowState.EXCLUSIVE.toString() %>"/>
<portlet:renderURL var="viewEmpRenderURL">
	<portlet:param name="mvcRenderCommandName" value="<%=MVCCommandNames.VIEW_EMPLOYEE_LIST_RENDER_COMMAND %>" />
</portlet:renderURL>
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
				<div class="col-md-12">
					<div class="col-12 radio-buttons">
						<div class="form-check-inline">
							<label for="<portlet:namespace/>imageCaptcha"
								class="form-check-label RadioCaptcha radioActive imageholder" tabindex="0"> <input
								type="radio" class="form-check-input imageHolder"
								id="<portlet:namespace/>imageCaptcha" value="image"
								name="<portlet:namespace/>captchaType" > <liferay-ui:message key="img_captcha" />
							</label>
						</div>
						<div class="form-check-inline">
							<label for="<portlet:namespace/>audioCaptcha"
								class="form-check-label RadioCaptcha audioholder" tabindex="0"> <input
								type="radio" class="form-check-input audioHolder"
								id="<portlet:namespace/>audioCaptcha" value="audio"
								name="<portlet:namespace/>captchaType" > <liferay-ui:message key="audio_captcha" />
							</label>
						</div>
						<aui:input name="captchaTypeHidden" id="captchaTypeHidden" type="hidden" label="captchaType" value="" />
						<div class="captcha-img-wrap">
							<liferay-captcha:captcha/>
						</div>
						<div class="captcha-audio-wrap">
							<div class="loader_background hide">
								<!-- <div class="loader"></div> -->
								<strong>Loading...</strong>
							</div>	
							<audio id="audioCaptchaTag" controls>
								<source src="" type="audio/mpeg">
							</audio>
							<div title="Refresh Audio" id="refreshAudio" class="refreshAudio hide">
								<img class="icon-reload" src="<%= request.getContextPath() %>/images/ico_refresh_capcha.svg" alt="reload-captcha" >
							</div>
						</div>
						<div class="audioCaptchaNotAvailableDiv" style="text-align: center;" type="hidden">
							<span style="border:1px solid red; font-size: 22px;"><liferay-ui:message key="audio-captcha-not-available"/></span>
						</div>
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
	$(document).ready(function() {
		
		$("#<portlet:namespace/>captchaTypeHidden").val("image");
		$("#<portlet:namespace/>imageCaptcha").attr('checked',true);
		$("#audioCaptchaTag").addClass("hide");
		$(".audioCaptchaNotAvailableDiv").addClass("hide");
		
		//working of image captach radio button
		$("#<portlet:namespace/>imageCaptcha").click(function() {
			$(".imageCaptcha").prop("checked", true);
			var captchaType = $(this).val();
			$("#<portlet:namespace/>captchaTypeHidden").val(captchaType);
			$("img.captcha").removeClass("hide");
			//$(".refresh").removeClass("hide");
			$("#audioCaptchaTag").addClass("hide");
			$("#refreshAudio").addClass("hide");
			$(".loader_background").addClass("hide");
			$(".captcha-img-wrap").removeClass("hide");
			$(".audioCaptchaNotAvailableDiv").addClass("hide");
			$("audio").hide();
		});
		
		$("#<portlet:namespace/>audioCaptcha").click(function() {
			$(".audioCaptcha").prop("checked",true);
			var captchaType = $(this).val();
			$("#<portlet:namespace/>captchaTypeHidden").val(captchaType);
			$("img.captcha").addClass("hide");
			//$(".refresh").addClass("hide");
			$("#audioCaptchaTag").addClass("hide");
			$(".captcha-img-wrap").addClass("hide");
			$(".audioCaptchaNotAvailableDiv").removeClass("hide");
/*			$("#audioCaptchaTag").removeClass("hide");
			$("#refreshAudio").removeClass("hide");
			$(".loader_background").removeClass("hide");
			$("audio").show();
*/

//			$.ajax({
//				method : "POST",
//				url : "<%=getAudioCaptchaURL%>",
//				success : function(data) {
//					$("#audioCaptchaTag").attr("src",data);
//					$("#audioCaptchaTag").removeClass("hide");
//					$(".loader_background").addClass("hide");
//				},
//				error : function(e) {
//				}
//			});
//
		});
	});
	
</script>