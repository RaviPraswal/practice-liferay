<%@page import="java.util.TreeSet"%>
<%@page import="com.liferay.test.employees.service.EmployeeLocalServiceUtil"%>
<%@page import="java.util.Set"%>
<%@page import="javax.portlet.PortletSession"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.petra.string.StringPool"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.test.employees.constants.MVCCommandNames"%>
<%@page
	import="com.liferay.test.employees.constants.PracticeLiferayEmployeesWebPortletKeys"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.test.employees.model.Employee"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="init.jsp"%>

<%
	List<Employee> employees = (List<Employee>) renderRequest.getAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_LIST_IN_RENDER_REQUEST);
	Set<String> firstNameSet = (Set<String>)renderRequest.getAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_FIRST_NAME_LIST_IN_RENDER_REQUEST);
	int delta = (Integer) renderRequest.getAttribute(PracticeLiferayEmployeesWebPortletKeys.DELTA_IN_RENDER_REQUEST);
	long employeeListSize = (Long) renderRequest.getAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_LIST_SIZE_IN_RENDER_REQUEST);
	String[] selectedFirstName = Validator.isNotNull(renderRequest.getPortletSession().getAttribute(PracticeLiferayEmployeesWebPortletKeys.SELECTED_FIRST_NAME_IN_RENDER_REQUEST,PortletSession.APPLICATION_SCOPE))? (String[]) renderRequest.getPortletSession().getAttribute(PracticeLiferayEmployeesWebPortletKeys.SELECTED_FIRST_NAME_IN_RENDER_REQUEST,PortletSession.APPLICATION_SCOPE): (new String[0]);
%>

<liferay-portlet:renderURL varImpl="viewEmployeesMVCRenderCommandURL">
	<portlet:param name="mvcRenderCommandName"
		value="<%=MVCCommandNames.VIEW_EMPLOYEE_LIST_RENDER_COMMAND%>" />
</liferay-portlet:renderURL>


<div class="container">
	<div class="row">
	    <div class="col">
			<div class="print-export-btn-wrapper">
				<a href="javascript:void(0)" class="admin-btn-icon print-btn" id="print-notification-feedbacks" onclick="printData();"><liferay-ui:message key="print" /></a> 
				<a href="javascript:void(0)" class="admin-btn-icon export-btn" id="export-notification-feedbacks"><liferay-ui:message key="export-in-csv" /></a>
			</div>
	    </div>
	</div>
	<div class="row">
	  	<div class="employee-list-filter">
			<form action="<%=viewEmployeesMVCRenderCommandURL%>" id="employeeFilter" method="post">
				<div class="row">		
		    		<div class="col">
		      			<div class="first-name-filter">
							<select label="First Name" name="<portlet:namespace/>listofFirstName" id="listofFirstName" multiple="multiple" class="d-none">
								<%
									if (Validator.isNotNull(firstNameSet)) {
										for (String firstName : firstNameSet) {
											if (Validator.isNotNull(Arrays.asList(selectedFirstName)) && Arrays.asList(selectedFirstName).size() > 0 &&  Arrays.asList(selectedFirstName).size() > 0 && Arrays.asList(selectedFirstName).contains(firstName)) {
								%>
												<option value="<%=firstName%>" selected="true"><%=firstName%></option>
								<%
											} else {
								%>
												<option value="<%=firstName%>"><%=firstName%></option>
								<%
											}
										}
									}
								%>
							</select>
						</div>
		    		</div>
				    <div class="col-5">
				      <input type="text" class="form-control" name="<portlet:namespace/>searchBar" placeholder="Search" />
				    </div>
				    <div class="col">
				    	<div class="row">
					    	<div class="col-12">
							    <aui:input type="hidden" readonly="true" id="clearFilters" name="clearFilters" value="false"></aui:input>
								<aui:button name="submitButton" type="submit" value="Apply Filter" />
								<aui:button id="clearButton" name="clearButton" type="button" value="Clear Filter" />
					    	</div>
				    	</div>
				    </div>
				</div>
		    </form>
		</div>
	</div>
	<div class="row">
		<div class="view-employee-list-search-container" id="viewEmployeeList">
			<liferay-ui:search-container var="searchContainer"
				emptyResultsMessage="No data found" delta="${delta }"
				total="${employeeListSize}"
				iteratorURL="<%=viewEmployeesMVCRenderCommandURL%>">
				<liferay-ui:search-container-results results="<%=employees%>" />
				<liferay-ui:search-container-row className="com.liferay.test.employees.model.Employee" modelVar="employee">
					<liferay-ui:search-container-column-text property="firstName" name="First Name" />
					<liferay-ui:search-container-column-text property="middleName" name="Middle Name" />
					<liferay-ui:search-container-column-text property="lastName" name="Last Name" />
					<liferay-ui:search-container-column-text property="phone" name="Phone Number" />
					<liferay-ui:search-container-column-text property="email" name="Email ID" />
					<liferay-ui:search-container-column-text property="dob" name="Date of Birth" />
					<%
						String status = WorkflowConstants.getStatusLabel(employee.getStatus());
						String statusColor = StringPool.BLANK;
						if (employee.getStatus() == WorkflowConstants.STATUS_APPROVED)
							statusColor = "color-green";
						else if (employee.getStatus() == WorkflowConstants.STATUS_PENDING)
							statusColor = "color-blue";
						else if (employee.getStatus() == WorkflowConstants.STATUS_DENIED)
							statusColor = "color-red";
					%>
					<liferay-ui:search-container-column-text cssClass="<%=statusColor%>" value="<%=status%>" name="Employee Registration Request" />
					<liferay-ui:search-container-column-text property="statusDate" name="Status Last Updated Date" />
				</liferay-ui:search-container-row>
				<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
			</liferay-ui:search-container>
		</div>
	</div>
</div>


<script>
	function printData() {
		var printContents = document.getElementsByClassName("table-responsive");
		console.log("Print Contents : "+printContents[0].innerHTML);
		mywindow = window.open("");
		mywindow.document.write('<html><head><title></title>');
		mywindow.document.write('</head><body >');
		mywindow.document.write(printContents[0].innerHTML);
		mywindow.document.write('</body></html>');
		mywindow.document.write('<style>table, th, td, tr {border: 1px solid;border-collapse: collapse;margin: auto;}</style>');
		mywindow.print();
		mywindow.close();
	}
	
	$('#listofFirstName').multiselect({
		buttonClass : 'form-control',
		numberDisplayed: 1,
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		nonSelectedText : 'Select',
	});

	$("#<portlet:namespace/>clearButton").on('click', function() {
		$("#<portlet:namespace/>clearFilters").val("true");
		if($("#employeeFilter")[0]){
			$("#employeeFilter").submit();
		} else {
			$("#<portlet:namespace/>employeeFilter").submit();
		}
	});
</script>

<style>
	.color-red {
		color: red;
	}
	
	.color-green {
		color: limegreen;
	}
	
	.color-blue {
		color: royalBlue;
	}
	
	.employee-list-filter{
		width: 100%;
	}
</style>


