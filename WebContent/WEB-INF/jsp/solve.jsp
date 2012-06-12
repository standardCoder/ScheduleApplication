<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.LinkedList,spring3.form.ScheduleHandler, complexschedulingproblem.WeekSchedule,
complexschedulingproblem.Shift, complexschedulingproblem.StaffMember, complexschedulingproblem.UsefulMethods,
complexschedulingproblem.Config" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Employees</h1>
<% 
	ScheduleHandler sh = (ScheduleHandler) session.getAttribute("scheduleHandler");
	WeekSchedule ws = sh.getWeekSchedule();
	LinkedList<Shift> shifts = ws.getShifts();
	LinkedList<StaffMember> staffMembers = ws.getStaffMembers();
	for(StaffMember staffMember:staffMembers){

		%>
		
		<p><%= staffMember.getName() %> is working the following shifts</p>
		
		<%
		for(Shift shift:UsefulMethods.getStaffsAssignedShifts(ws.getShifts(), staffMember)){
			%><p>from <%= shift.getShiftTimes().getStart().toString("EEEE HH:mm") %> to 
					  <%= shift.getShiftTimes().getEnd().toString("EEEE HH:mm") %> </p>  <%
		}
            
		
	}
%>
</body>
</html>