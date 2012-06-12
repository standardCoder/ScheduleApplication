/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complexschedulingproblem;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;


import org.joda.time.DateTime;
import org.joda.time.Interval;

import spring3.form.Employee;

/**
 *
 * @author seb
 */
public class StaffMember {
    private String Name;
    private LinkedList<Skill> possessedSkills = new LinkedList<Skill>();
    private LinkedList<Interval> availableIntervals = new LinkedList<Interval>();
    //private LinkedList<Shift> assignedShifts = new LinkedList<Shift>();    This is stored in the shift
    private int maxHoursPerWeek;

    public StaffMember(String Name){
        int noAvailableIntervals = 2;
        Random randomGenerator = new Random();
        DateTime dt1 = Config.testStartDate;
        DateTime dt2 = Config.testEndDate;
        DateTime[] dates = UsefulMethods.generateRandomDates(noAvailableIntervals, 30, dt1, dt2);
        for(int i=0; i<dates.length/noAvailableIntervals; i++){
            availableIntervals.add(new Interval(dates[i],dates[i+1]));
        }
        possessedSkills = UsefulMethods.getRandomSkills();
        maxHoursPerWeek = randomGenerator.nextInt(25) + 10;
        this.Name = Name;
    }

    public StaffMember(String Name, LinkedList<Skill> possessedSkills, Interval availableIntervals, int maxHoursPerWeek) {
        this.Name = Name;
        this.possessedSkills = possessedSkills;
        LinkedList<Interval> times = new LinkedList<Interval>();
        times.add(availableIntervals);
        this.availableIntervals = times;
        this.maxHoursPerWeek = maxHoursPerWeek;
    }

    public StaffMember(String name, String[] skills, 
            String[] availableStartDay, String[] availableStartHour, String[] availableStartMinutes, 
            String[] availableEndDay, String[] availableEndHour, String[] availableEndMinutes, 
            String maxHours, Map<String,Skill> skillsMap){
        this.Name = name;
        //This sets the day to sunday then adds on the available day and time
        for(int i=0;i<availableStartDay.length;i++){
            DateTime startAvailableDateTime = new DateTime(1970,1,4+Integer.parseInt(availableStartDay[i]),
                    Integer.parseInt(availableStartHour[i]), Integer.parseInt(availableStartMinutes[i]));
            DateTime endAvailableDateTime = new DateTime(1970,1,4+Integer.parseInt(availableEndDay[i]),
                    Integer.parseInt(availableEndHour[i]), Integer.parseInt(availableEndMinutes[i]));
            availableIntervals.add(new Interval(startAvailableDateTime, endAvailableDateTime));
        }
        //This maps the string of the skills name to the skill
        for(String skill:skills){
            possessedSkills.add(skillsMap.get(skill));
        }
        maxHoursPerWeek = Integer.getInteger(maxHours);
    }
    
    public StaffMember(Employee employee, Map<String,Skill> skillsMap){
        this.Name = employee.getName();
        //This sets the day to sunday then adds on the available day and time
        String[] availableStartDay = employee.getAvailableStartDay();
        String[] availableStartHour = employee.getAvailableStartHour();
        String[] availableStartMinutes = employee.getAvailableStartMinutes();
        String[] availableEndDay = employee.getAvailableEndDay();
        String[] availableEndHour = employee.getAvailableEndHour();
        String[] availableEndMinutes = employee.getAvailableEndMinutes();
        
        for(int i=0;i<employee.getAvailableStartDay().length;i++){
        	
            DateTime startAvailableDateTime = new DateTime(1970,1,4+Integer.parseInt(availableStartDay[i]), 
            		Integer.parseInt(availableStartHour[i]), Integer.parseInt(availableStartMinutes[i]));
        	DateTime endAvailableDateTime = new DateTime(1970, 1, 4+Integer.parseInt(availableEndDay[i]), 
        			Integer.parseInt(availableEndHour[i]), Integer.parseInt(availableEndMinutes[i]));
            availableIntervals.add(new Interval(startAvailableDateTime, endAvailableDateTime));
        }
        //This maps the string of the skills name to the skill
        for(String skill:employee.getSkills()){
            possessedSkills.add(skillsMap.get(skill));
        }
        maxHoursPerWeek = Integer.parseInt(employee.getMaxHours());
    }
    
    public int getMaxHoursPerWeek() {
        return maxHoursPerWeek;
    }

    public void setMaxHoursPerWeek(int maxHoursPerWeek) {
        this.maxHoursPerWeek = maxHoursPerWeek;
    }

    public LinkedList<Skill> getPossessedSkills() {
        return possessedSkills;
    }

    public LinkedList<Interval> getAvailableIntervals() {
        return availableIntervals;
    }

    public void setAvailableIntervals(LinkedList<Interval> availableIntervals) {
        this.availableIntervals = availableIntervals;
    }

    public String getName() {
        return Name;
    }
    
    public void printStaffDetail(WeekSchedule weekSchedule){
        System.out.println("\n******************STAFF MEMBER "+Name+"******************");
        System.out.println("Available Hours");
        for(int j=0; j<availableIntervals.size(); j++)
            System.out.println(
                    availableIntervals.get(j).getStart().toString(Config.dateFormat)
                    +"  To  "
                    +availableIntervals.get(j).getEnd().toString(Config.dateFormat));
        System.out.println("Max Hours Per Week\n"+maxHoursPerWeek);
        System.out.println("Possessed Skills");
        for(int j=0; j<possessedSkills.size(); j++)
            System.out.println(" -"+possessedSkills.get(j).getName());
        System.out.println("Assigned Shifts");
        for(Shift shift:UsefulMethods.getStaffsAssignedShifts(weekSchedule.getShifts(), this))
            System.out.println("  "+shift.getShiftTimes().getStart().toString(Config.dateFormat)
                    +"  to  "+shift.getShiftTimes().getEnd().toString(Config.dateFormat));
    }
}
//    private String Name;
//    private LinkedList<Skill> possessedSkills = new LinkedList<Skill>();
//    private LinkedList<Interval> availableIntervals = new LinkedList<Interval>();
//    //private LinkedList<Shift> assignedShifts = new LinkedList<Shift>();    This is stored in the shift
//    private int maxHoursPerWeek