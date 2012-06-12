<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.LinkedList,spring3.form.FormSkill,spring3.form.ScheduleHandler" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	
	ScheduleHandler sh = (ScheduleHandler) session.getAttribute("scheduleHandler");
	if(sh.getSkills() != null){
	LinkedList<FormSkill> formSkills = sh.getSkills();
	%><h2>Skills</h2><%
	for(FormSkill formSkill:formSkills){ 
%>
		<p><%= formSkill.getName() %></p>
<%
	} 
}
%>

<h2>Add Skill</h2>
	<form:form method="post" action="addSkill.html" commandName="newSkill">
	 
        <form:label path="name">Skill Name</form:label>
        <form:input path="name" />
        <input type="submit" value="Add Skill"/>
	
	</form:form>
	<p><a href="home.html">home</a></p>
</body>
</html>