package spring3.form;

import java.util.LinkedList;

import complexschedulingproblem.WeekSchedule;

public class ScheduleHandler {
	private LinkedList<Employee> employees = new LinkedList<Employee>();
	private LinkedList<FormShift> formShifts = new LinkedList<FormShift>();
	private LinkedList<FormSkill> formSkills = new LinkedList<FormSkill>();
	private WeekSchedule weekSchedule;
	
	public void addEmployee(Employee employee){
		employees.add(employee);
	}
	public LinkedList<Employee> getEmployees(){
		return employees;
	}
	public void addShift(FormShift formShift){
		formShifts.add(formShift);
	}
	public LinkedList<FormShift> getShifts(){
		return formShifts;
	}
	public void addSkill(FormSkill formSkill){
		formSkills.add(formSkill);
	}
	public LinkedList<FormSkill> getSkills(){
		return formSkills;
	}
	
	public WeekSchedule getWeekSchedule(){
		return weekSchedule;
	}
	public void createSchedule(){
		weekSchedule = new WeekSchedule(employees, formShifts, formSkills);
	}
}
