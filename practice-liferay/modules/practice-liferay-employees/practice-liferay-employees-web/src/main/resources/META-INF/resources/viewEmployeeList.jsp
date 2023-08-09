<%@page import="com.liferay.petra.string.StringPool"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.test.employees.constants.MVCCommandNames"%>
<%@page import="com.liferay.test.employees.constants.PracticeLiferayEmployeesWebPortletKeys"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.test.employees.model.Employee"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="init.jsp" %>

<%
	List<Employee> employees = (List<Employee>)renderRequest.getAttribute(PracticeLiferayEmployeesWebPortletKeys.EMPLOYEE_LIST_IN_RENDER_REQUEST);
%>

<liferay-portlet:renderURL varImpl="viewEmployeesMVCRenderCommandURL">
	<portlet:param name="mvcRenderCommandName" value="<%=MVCCommandNames.VIEW_EMPLOYEE_LIST_RENDER_COMMAND%>" />
</liferay-portlet:renderURL>

<div class="container">
	<div class="col-lg-4 col-12">
		<div class="print-export-btn-wrapper">
			<a href="javascript:void(0)" class="admin-btn-icon print-btn" id="print-notification-feedbacks" onclick="printData();"><liferay-ui:message key="print" /></a>
			<a href="javascript:void(0)" class="admin-btn-icon export-btn" id="export-notification-feedbacks"><liferay-ui:message key="export-in-csv" /></a>
		</div>
	</div>
	<div class="" id="viewEmployeeList">
		<liferay-ui:search-container
			deltaConfigurable="true"
			var="searchContainer" emptyResultsMessage="No data found" delta="10"
			total="<%=employees.size()%>"
			iteratorURL="${viewEmployeesMVCRenderCommandURL}"
		>
			<liferay-ui:search-container-results
				results="<%=ListUtil.subList(employees, searchContainer.getStart(),searchContainer.getEnd())%>"/>
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
					if(employee.getStatus() == WorkflowConstants.STATUS_APPROVED)
						statusColor = "color-green";
					else if(employee.getStatus() == WorkflowConstants.STATUS_PENDING)
						statusColor = "color-blue";
					else if(employee.getStatus() == WorkflowConstants.STATUS_DENIED)
						statusColor = "color-red";
				%>
				<liferay-ui:search-container-column-text cssClass="<%=statusColor %>" value="<%=status%>" name="Employee Registration Request" />
				<liferay-ui:search-container-column-text property="statusDate" name="Status Last Updated Date" />
			</liferay-ui:search-container-row>
			<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
		</liferay-ui:search-container>
	</div>
</div>


<script>
function printData() {
	console.log("--------------");
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
</script>


<style>
.color-red{
	color: red;
}
.color-green{
	color: limegreen;
}
.color-blue{
	color: royalBlue;
}
</style>


