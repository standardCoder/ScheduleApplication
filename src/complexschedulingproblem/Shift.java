/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complexschedulingproblem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


import org.joda.time.DateTime;
import org.joda.time.Interval;

import spring3.form.FormShift;

import java.util.Random;

/**
 *
 * @author seb
 */
public class Shift {

    private Interval shiftTimes;
    private LinkedList<Skill> skillsNeeded;
    private StaffMember assignedStaffMember;
    private LinkedList<StaffMember> domainOfStaffMembers;
    
    public Shift (){
        randomAssign();
    }
    
    public Shift(LinkedList<StaffMember> staffMembers){
        this.domainOfStaffMembers = UsefulMethods.copyLinkedList(staffMembers);
        randomAssign();
    }

    public Shift(DateTime startTime, int durationHours, LinkedList<Skill> skillsNeeded, 
            LinkedList<StaffMember> domainOfStaffMembers) {
        this.shiftTimes = new Interval(startTime, new DateTime(startTime.plusHours(durationHours)));
        this.skillsNeeded = skillsNeeded;
        this.domainOfStaffMembers = domainOfStaffMembers;
    }
    
    public Shift(String day, String startHour, String startMinutes, String durationHours, 
            String durationMinutes, String endTime, String[] skills, Map<String, Skill> skillsMap){
    	skillsNeeded = new LinkedList<Skill>();

        DateTime startDateTime = new DateTime(1970,1,4+Integer.parseInt(day),
                Integer.parseInt(startHour), Integer.parseInt(startMinutes));
        
        DateTime endDateTime = new DateTime(
                startDateTime.plusHours(Integer.parseInt(durationHours)).plusMinutes(
                Integer.parseInt(durationMinutes)));
        
        shiftTimes = new Interval(startDateTime, endDateTime);
        
        for(String skill:skills){
            skillsNeeded.add(skillsMap.get(skill));
        }
    }
    
    public Shift(FormShift formShift, Map<String, Skill> skillsMap){
    	skillsNeeded = new LinkedList<Skill>();
        DateTime startDateTime = new DateTime(1970,1,4+Integer.parseInt(formShift.getDay()),
                Integer.parseInt(formShift.getStartHour()), Integer.parseInt(formShift.getStartMinutes()));
        
        DateTime endDateTime = new DateTime(
                startDateTime.plusHours(Integer.parseInt(formShift.getDurationHour())).plusMinutes(
                Integer.parseInt(formShift.getDurationMinutes())));
        
        shiftTimes = new Interval(startDateTime, endDateTime);
        
        for(String skill:formShift.getSkills()){
            skillsNeeded.add(skillsMap.get(skill));
        }
    }

//    The set Staff Member will only be accessable from the shift not the staff member. 
    public void setAssignedStaffMember(StaffMember assignedStaffMember) {
        this.assignedStaffMember = assignedStaffMember;
    }

    public Interval getShiftTimes() {
        return shiftTimes;
    }

    public void setShiftTimes(Interval shiftTimes) {
        this.shiftTimes = shiftTimes;
    }

    public LinkedList<Skill> getSkillsNeeded() {
        return skillsNeeded;
    }

    public StaffMember getAssignedStaffMember() {
        return assignedStaffMember;
    }

    public LinkedList<StaffMember> getDomainOfStaffMembers() {
        return domainOfStaffMembers;
    }

    public void setDomainOfStaffMembers(LinkedList<StaffMember> domainOfStaffMembers) {
        this.domainOfStaffMembers = domainOfStaffMembers;
    }
    
    public void updateUnaryConstraints(){
        for(Iterator<StaffMember> iter = domainOfStaffMembers.iterator(); iter.hasNext();){
            StaffMember staffMember = (StaffMember)iter.next();
            if(!ConstraintHandler.isStaffMemberAbleToWorkShift(this, staffMember)){
                iter.remove();
            } else if(!doesStaffMemberHaveTheSkill(staffMember))
                iter.remove();
        }
    }
    
    public void updateUnaryConstraints(LinkedList<StaffMember> allStaffMembers){
        domainOfStaffMembers = new LinkedList<StaffMember> (allStaffMembers);
        for(Iterator<StaffMember> iter = domainOfStaffMembers.iterator(); iter.hasNext();){
            StaffMember staffMember = (StaffMember)iter.next();
            if(!ConstraintHandler.isStaffMemberAbleToWorkShift(this, staffMember)){
                iter.remove();
            } else if (!doesStaffMemberHaveTheSkill(staffMember))
                iter.remove();
        }
    }
    
    private void randomAssign(){
        Random randomGenerator = new Random();
        DateTime startDateTime = UsefulMethods.generateRandomDates(
                1, Config.timeInterval, Config.testStartDate, Config.testEndDate.minusHours(10))[0];
        int durationInHours = randomGenerator.nextInt(10);
        shiftTimes = new Interval(startDateTime,startDateTime.plusHours(durationInHours));
        skillsNeeded = UsefulMethods.getRandomSkills();
    }
    
    public void randomAssignStaffMember(){
        if(domainOfStaffMembers.size() > 0){
            Random randomGenerator = new Random();
            int i = randomGenerator.nextInt(domainOfStaffMembers.size());
            assignedStaffMember = domainOfStaffMembers.get(i);
        }
    }
    
    public boolean doesStaffMemberHaveTheSkill(StaffMember staffMember){
        boolean doesStaffMemberHaveTheSkill = false;
        if(staffMember.getPossessedSkills().containsAll(skillsNeeded))
            doesStaffMemberHaveTheSkill = true;
        return doesStaffMemberHaveTheSkill;
    }
    
    public boolean doesStaffMemberHaveTheSkill(){
        boolean doesStaffMemberHaveTheSkill = false;
        if(assignedStaffMember != null)
            if(assignedStaffMember.getPossessedSkills().containsAll(skillsNeeded))
                doesStaffMemberHaveTheSkill = true;
        return doesStaffMemberHaveTheSkill;
    }
    
    public void printShiftDetail(){
        System.out.println("\n******************SHIFT "
                +shiftTimes.getStart().toString(Config.dateFormat)+"******************");
        System.out.println("Start Time "+shiftTimes.getStart().toString(Config.dateFormat)
                +"      End Time "+shiftTimes.getEnd().toString(Config.dateFormat));
        if(skillsNeeded!=null){    
            System.out.println("Skills Needed");
            for(int j=0; j<skillsNeeded.size(); j++)
                System.out.println(" -"+skillsNeeded.get(j).getName());
        }
        System.out.println("Domain of Staff");
        for(StaffMember staffMember:domainOfStaffMembers)
            System.out.println(staffMember.getName()+"     ");
        if(assignedStaffMember != null)
            System.out.println("\nAssigned Staff Member \n"+assignedStaffMember.getName());
    }
}
