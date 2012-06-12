/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complexschedulingproblem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.joda.time.DateTime;

import spring3.form.Employee;
import spring3.form.FormShift;
import spring3.form.FormSkill;

/**
 *
 * @author seb
 */
public class WeekSchedule {
    private DateTime startDate;
    private LinkedList<Shift> shifts = new LinkedList<Shift>();
    private LinkedList<StaffMember> staffMembers = new LinkedList<StaffMember>();
    private LinkedList<Skill> skills;
    
    public WeekSchedule(LinkedList<Employee> employees,LinkedList<FormShift> formShifts,
    		LinkedList<FormSkill> formSkills){
    	startDate = new DateTime(1970,1,5,0,0);
    	
    	//add Skills and skillsMap
    	skills = new LinkedList<Skill>();
    	Map<String, Skill> skillsMap = new HashMap<String, Skill>();
    	for(FormSkill formSkill:formSkills){
    		formSkill.createSkill();
    		skillsMap.put(formSkill.getName(), formSkill.getSkill());
    		skills.add(formSkill.getSkill());
    	}
    	
    	//add employees as staffMembers
    	for(Employee employee:employees){
    		staffMembers.add(new StaffMember(employee,skillsMap));
    	}
    	
    	//add formShifts and Shifts
    	for(FormShift formShift:formShifts){
    		shifts.add(new Shift(formShift, skillsMap));
    	}
    	
    	updateAllUnaryConstraints();
    	
    	UsefulMethods.LocalSearch(this, 30);
    }
    
    public WeekSchedule(int shift, int Staff) {
        int noOfShifts = shift, noOfStaff = Staff;
        this.startDate = Config.testStartDate;
        for(int i=0; i<noOfStaff; i++)
            this.staffMembers.add(new StaffMember(Integer.toString(i)));
        for(int i=0; i<noOfShifts; i++)
            this.shifts.add(new Shift(this.staffMembers));
        this.skills = Config.getSkills();
        updateAllUnaryConstraints();
        UsefulMethods.assignRandomShifts(this.shifts,this.staffMembers);
    }

    public WeekSchedule(DateTime startDate, LinkedList<Shift> Shifts, LinkedList<StaffMember> staffMembers, LinkedList<Skill> skills) {
        this.startDate = startDate;
        this.shifts = Shifts;
        this.staffMembers = staffMembers;
        this.skills = skills;
    }
    
    public LinkedList<Shift> getShifts() {
        return shifts;
    }

    public LinkedList<StaffMember> getStaffMembers() {
        return staffMembers;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public LinkedList<Skill> getSkills() {
        return skills;
    }
    
    public void updateAllUnaryConstraintsPublic(){
        this.updateAllUnaryConstraints();
    }
    
    private void updateAllUnaryConstraints(){
        for(Shift shift:shifts)
            shift.updateUnaryConstraints(staffMembers);
    }
    
    public void printAllDetails(){
        System.out.println("\n\n\n\n****Begin Schedule print out****");
        System.out.println("StartDate "+startDate.toString(Config.dateFormat));
        for(Shift shift:shifts)
            shift.printShiftDetail();
        for(StaffMember staffMember:staffMembers)
            staffMember.printStaffDetail(this);
    }
    
    public void randomAssignAllShiftsStaffMember(){
        for(Shift shift:shifts)
            shift.randomAssignStaffMember();
        if(checkForNonEmptyDomains() != 0){
            System.out.println("ERROR: there are "+checkForNonEmptyDomains()+" shifts with empty domains");
            printAllDetails();
            System.exit(-1);
        }
        
    }
    
    private int checkForNonEmptyDomains(){
        int noOfShiftsWithEmptyDomains = 0;
        for(Shift shift:shifts){
            if(shift.getDomainOfStaffMembers().size() == 0)
                noOfShiftsWithEmptyDomains++;
        }
        return noOfShiftsWithEmptyDomains;
    }
}
