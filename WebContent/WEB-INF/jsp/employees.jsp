<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.LinkedList,spring3.form.Employee,spring3.form.ScheduleHandler" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style type="text/css">

#timing{

}
#formElement{
margin:10px 0 0 0;
}

</style>

</head>
<body>
<h2>Employees</h2>
<%
	ScheduleHandler sh = (ScheduleHandler) session.getAttribute("scheduleHandler");

	if(sh.getEmployees() != null){
		LinkedList<Employee> Employees = sh.getEmployees();
		for(Employee employee:Employees){
%>
	<p>NAME: <%= employee.getName() %> </p><p>    MAXHOURS: <%= employee.getMaxHours() %></p>     <p>SKILLS
<%
		for(String skill:employee.getSkills()){
			%><%= skill %><%
		}%></p>     <p>AVAILABLE FROM: Day <%= employee.getAvailableStartDay()[0] %> AT Time: <%= employee.getAvailableStartHour()[0] %>:<%= employee.getAvailableStartMinutes()[0] %></p>     
		<p>AVAILABLE TO: Day <%= employee.getAvailableEndDay()[0] %> AT Time <%= employee.getAvailableEndHour()[0] %>:<%= employee.getAvailableEndMinutes()[0] %></p>
		<hr/>
		<br/>
		<br/>	
		<%
		
	} 
}
%>

<h2>Add Employee</h2>
	<form:form method="post" action="employees/add.html" commandName="newEmployee">
	
	    <form:label path="name">Name</form:label>
		<form:input path="name" />
		
		
		<div id="timing">
			<p id="formElement">Working Period</p>
			<form:select path="availableStartDay" multiple="false">
				<form:option value="NONE" label="--- start day ---"/>
				<form:options items="${dayList}" />
			</form:select>
			
			<form:select path="availableStartHour" multiple="false">
				<form:option value="NONE" label="--- start hour ---"/>
				<form:options items="${hoursOfDayList}" />
			</form:select>
			
			<form:select path="availableStartMinutes" multiple="false">
				<form:option value="NONE" label="--- start minute ---"/>
				<form:options items="${minutesOfDayList}" />
			</form:select>
		</div>
		
		<div id="timing">
			<form:select path="availableEndDay" multiple="false">
				<form:option value="NONE" label="--- end day ---"/>
				<form:options items="${dayList}" />
			</form:select>
			
			<form:select path="availableEndHour" multiple="false">
				<form:option value="NONE" label="--- end hour ---"/>
				<form:options items="${hoursOfDayList}" />
			</form:select>
			
			<form:select path="availableEndMinutes" multiple="false">
				<form:option value="NONE" label="--- end minutes ---"/>
				<form:options items="${minutesOfDayList}" />
			</form:select>
		</div>
		
		<div id="formElement">
			<form:label path="maxHours">Maximum Working Hours</form:label>
			<form:input path="maxHours" />
		</div>
		
		<div id="formElement">
		<form:label path="skills">Possessed Skills</form:label>
		<form:checkboxes items="${skillsList}" path="skills" />
		</div>
		
		<div id="formElement">
			<input type="submit" value="Add Employee"/>
 		</div>
	 
	</form:form>
	<p><a href="home.html">home</a></p>
</body>
</html>