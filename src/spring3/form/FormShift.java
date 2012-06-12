package spring3.form;


public class FormShift {
	
	private String day;
	private String startHour;
	private String startMinutes;
	private String durationHour;
	private String durationMinutes;
	private String[] skills;
	
	
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getStartMinutes() {
		return startMinutes;
	}
	public void setStartMinutes(String startMinutes) {
		this.startMinutes = startMinutes;
	}
	public String getDurationHour() {
		return durationHour;
	}
	public void setDurationHour(String durationHour) {
		this.durationHour = durationHour;
	}
	public String getDurationMinutes() {
		return durationMinutes;
	}
	public void setDurationMinutes(String durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String[] getSkills() {
		return skills;
	}
	public void setSkills(String[] skills) {
		this.skills = skills;
	}
}
