package spring3.form;

public class Employee {
	
    private String name;
    private String[] skills;
    private String[] availableStartDay;
    private String[] availableStartHour;
    private String[] availableStartMinutes;
    private String[] availableEndDay;
    private String[] availableEndHour;
    private String[] availableEndMinutes;
    private String maxHours;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getSkills() {
		return skills;
	}
	public void setSkills(String[] skills) {
		this.skills = skills;
	}
	public String[] getAvailableStartDay() {
		return availableStartDay;
	}
	public void setAvailableStartDay(String[] availableStartDay) {
		this.availableStartDay = availableStartDay;
	}
	public String[] getAvailableStartHour() {
		return availableStartHour;
	}
	public void setAvailableStartHour(String[] availableStartHour) {
		this.availableStartHour = availableStartHour;
	}
	public String[] getAvailableStartMinutes() {
		return availableStartMinutes;
	}
	public void setAvailableStartMinutes(String[] availableStartMinutes) {
		this.availableStartMinutes = availableStartMinutes;
	}
	public String[] getAvailableEndDay() {
		return availableEndDay;
	}
	public void setAvailableEndDay(String[] availableEndDay) {
		this.availableEndDay = availableEndDay;
	}
	public String[] getAvailableEndHour() {
		return availableEndHour;
	}
	public void setAvailableEndHour(String[] availableEndHour) {
		this.availableEndHour = availableEndHour;
	}
	public String[] getAvailableEndMinutes() {
		return availableEndMinutes;
	}
	public void setAvailableEndMinutes(String[] availableEndMinutes) {
		this.availableEndMinutes = availableEndMinutes;
	}
	public String getMaxHours() {
		return maxHours;
	}
	public void setMaxHours(String maxHours) {
		this.maxHours = maxHours;
	}
}
