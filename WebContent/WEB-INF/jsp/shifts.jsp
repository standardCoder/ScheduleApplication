<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.LinkedList,spring3.form.FormShift,spring3.form.ScheduleHandler" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Shifts</h2>
<%
	
	ScheduleHandler sh = (ScheduleHandler) session.getAttribute("scheduleHandler");
	if(sh.getShifts() != null){
	LinkedList<FormShift> shifts = sh.getShifts();
	for(FormShift shift:shifts){%>
<p>Shift starts on day <%= shift.getDay() %> at time <%= shift.getStartHour() %>:<%= shift.getStartMinutes() %></p> 
	<p>and lasts for <%= shift.getDurationHour() %> hours and <%= shift.getDurationMinutes() %>minutes</p>
	<p>These skills are required: 
<%
		for(String skill:shift.getSkills()){
			%><%= skill %><%
		}
	%>
	<br/>
	<br/>
	<%
	} 
}
%>
</p>
	<h2>Add Shift</h2>

	<form:form method="post" action="addShift.html" commandName="newShift">
	 	
        <form:select path="day">
			<form:option value="NONE" label="--- Day ---"/>
			<form:options items="${dayList}" />
		</form:select>
		<form:select path="startHour">
			<form:option value="NONE" label="--- start hour ---"/>
			<form:options items="${hoursOfDayList}" />
		</form:select>
		<form:select path="startMinutes">
			<form:option value="NONE" label="--- start minutes ---"/>
			<form:options items="${minutesOfDayList}" />
		</form:select>
		<form:select path="durationHour">
			<form:option value="NONE" label="--- duration hour ---"/>
			<form:options items="${hoursOfDayList}" />
		</form:select>
		<form:select path="durationMinutes">
			<form:option value="NONE" label="--- duration minutes ---"/>
			<form:options items="${minutesOfDayList}" />
		</form:select>
		<form:checkboxes items="${skillsList}" path="skills" />
        <input type="submit" value="Add Shift"/>
	
	</form:form>
	<p><a href="home.html">home</a></p>

</body>
</html>