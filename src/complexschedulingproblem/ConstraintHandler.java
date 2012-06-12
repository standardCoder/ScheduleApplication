/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complexschedulingproblem;

import java.util.Collections;
import java.util.LinkedList;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 *
 * @author seb
 */
public class ConstraintHandler {

    public static int totalConstraintViolations(WeekSchedule weekSchedule){
        int totalConstraintViolations = 0;
        totalConstraintViolations += checkAllStaffForOverLappingShifts(weekSchedule.getShifts(), weekSchedule.getStaffMembers());
//        totalConstraintViolations += doAllStaffMembersHaveTheSkills(weekSchedule.getShifts()); this is taken care of in the unary constraint
        totalConstraintViolations += noStaffMembersExceedMaxHours(weekSchedule.getShifts(), weekSchedule.getStaffMembers());
        totalConstraintViolations += noOfStaffMembersExceedingMaxHoursPerDayForWeekSchedule(weekSchedule);
        return totalConstraintViolations;
    }
    
    public static int checkAllStaffForOverLappingShifts(LinkedList<Shift> shifts, LinkedList<StaffMember> staffMembers){
        int totalViolations = 0;
        for(StaffMember staffMember:staffMembers)
            totalViolations += checkForOverlappingShifts(shifts, staffMember);
        return totalViolations;
    }
    
//    Do for EACH STAFFMEMBER DONE
    public static int checkForOverlappingShifts(LinkedList<Shift> shifts, StaffMember staffMember){
        LinkedList<Shift> assignedShifts = UsefulMethods.getStaffsAssignedShifts(shifts, staffMember);
        Collections.sort(assignedShifts, new ShiftSortStartDate());
        int noOfOverLappingShifts = 0;
        for(int i=0; i<assignedShifts.size()-1; i++){
            if(assignedShifts.get(i).getShiftTimes().overlaps(assignedShifts.get(i+1).getShiftTimes())){
                noOfOverLappingShifts++;
//                System.out.println("\n\n*****These shifts overlap*****");
//                assignedShifts.get(i).printShiftDetail();
//                assignedShifts.get(i+1).printShiftDetail();
//                System.out.println("*****End these shifts overlap*****\n");
            }
        }
        return noOfOverLappingShifts;
    }
    
    public static int doAllStaffMembersHaveTheSkills(LinkedList<Shift> shifts){
        int totalViolations = 0;
        for(Shift shift:shifts)
            if(doesTheStaffMemberHaveTheSkills(shift))
                totalViolations++;
        return totalViolations;
    }
    
//    Do for EACH SHIFT DONE
    public static boolean doesTheStaffMemberHaveTheSkills(Shift shift){
//        StaffMember staffMember = shift.getAssignedStaffMember();
//        boolean skillsArePossessed  = false;
//        if(staffMember.getPossessedSkills().containsAll(shift.getSkillsNeeded()))
//            skillsArePossessed = true;
        return shift.doesStaffMemberHaveTheSkill();
    }
    
    public static int noStaffMembersExceedMaxHours(LinkedList<Shift> shifts, LinkedList<StaffMember> staffMembers){
        int totalViolations = 0;
        for(StaffMember staffMember:staffMembers)
            if(staffMemberExceedsMaxHours(shifts,staffMember))
                totalViolations++;
        return totalViolations;
    }
    
//    Do for EACH STAFFMEMBER DONE
    public static boolean staffMemberExceedsMaxHours(LinkedList<Shift> shifts, StaffMember staffMember){
        int noOfMinutesAssigned = UsefulMethods.getStaffMembersTotalAssignedMinutes(shifts, staffMember);
        boolean hoursExceeded = false;
//        System.out.print("staff Max mins: "+(staffMember.getMaxHoursPerWeek()*60)+" no of mins assigned: "+UsefulMethods.getStaffMembersTotalAssignedMinutes(shifts, staffMember));
        if(staffMember.getMaxHoursPerWeek()*60 <= noOfMinutesAssigned)
            hoursExceeded = true;
        return hoursExceeded;
    }
    
    public static int noOfStaffMembersExceedingMaxHoursPerDayForWeekSchedule(WeekSchedule weekSchedule){
        int totalViolations = 0;
        for(StaffMember staffMember:weekSchedule.getStaffMembers())
            totalViolations = staffMemberExceedsMaxHoursPerDayForWeekSchedule(staffMember, weekSchedule);
        return totalViolations;
    }
    
    public static int staffMemberExceedsMaxHoursPerDayForWeekSchedule(StaffMember staffMember, WeekSchedule weekSchedule){
        int totalViolations = 0;
        DateTime startDate = weekSchedule.getStartDate();
        for(int i=0; i<7; i++)
             if(staffMemberExceedsMaxHoursPerDay(weekSchedule.getShifts(), staffMember, startDate.plusDays(i)))
                 totalViolations++;
        return totalViolations;
    }
    
//    Do for EACH STAFFMEMBER EACH DAY
    public static boolean staffMemberExceedsMaxHoursPerDay(LinkedList<Shift> shifts, StaffMember staffMember, DateTime day){
        int overlapOfShiftAndDayMinutes = UsefulMethods.getAssignedMinutesForDay(shifts, staffMember, day);
        boolean staffMemberExceedsMaxHoursPerDay = false;
//        System.out.println("hours worked on this day: "+(overlapOfShiftAndDayMinutes/60));
        if(overlapOfShiftAndDayMinutes > 60*12)
            staffMemberExceedsMaxHoursPerDay = true;
        return staffMemberExceedsMaxHoursPerDay;
    }
    
    
    
    
    public static boolean isStaffMemberAbleToWorkShift(Shift shift, StaffMember staffMember){
        LinkedList<Interval> availableIntervals = staffMember.getAvailableIntervals();
        boolean ableToWorkShift = false;
        for(Interval interval:availableIntervals){
            if(interval.contains(shift.getShiftTimes())){
                ableToWorkShift = true;
                break;
            }
        }
        return ableToWorkShift;
    }
    
//    Do for EVERY SHIFT
    public static boolean isStaffMemberAbleToWorkAssignedShift(Shift shift){
        LinkedList<Interval> availableIntervals = shift.getAssignedStaffMember().getAvailableIntervals();
        boolean ableToWorkShift = false;
        for(int i=0; i<availableIntervals.size(); i++){
            if(availableIntervals.get(i).contains(shift.getShiftTimes()))
                ableToWorkShift = true;
        }
        return ableToWorkShift;
    }
    
    public static int noOfAssignedShiftsStaffMemberUnableToWork(LinkedList<Shift> shifts, StaffMember staffMember){
        LinkedList<Shift> assignedShifts = UsefulMethods.getStaffsAssignedShifts(shifts, staffMember);
        int noOfShiftsUnable = 0;
        for(Shift shift:assignedShifts)
            if(!isStaffMemberAbleToWorkAssignedShift(shift))
                noOfShiftsUnable++;
        return noOfShiftsUnable;
    }
    
    public static int noOfAssignedShiftsAllStaffUnableToWork(LinkedList<Shift> shifts, LinkedList<StaffMember> staffMembers){
        int numberOfShiftsUnableToWork = 0;
        for(StaffMember staffMember:staffMembers)
            numberOfShiftsUnableToWork += noOfAssignedShiftsStaffMemberUnableToWork(shifts, staffMember);
        return numberOfShiftsUnableToWork;
    }
}
