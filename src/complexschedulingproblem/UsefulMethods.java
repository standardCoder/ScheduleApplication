/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complexschedulingproblem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 *
 * @author seb
 */
public class UsefulMethods {

    private static Random randomGenerator = new Random();
    
    public static DateTime[] generateRandomDates(int numberOfDates, int minuteIncrements, DateTime startDate, DateTime endDate){
        Interval iv = new Interval(startDate, endDate);
        int differenceInTimeIncrements = (int) (iv.toDurationMillis()/(1000*60*minuteIncrements));
        int [] randomIncrements = new int[numberOfDates];
        for(int i=0; i<numberOfDates; i++){
            randomIncrements[i] = randomGenerator.nextInt(differenceInTimeIncrements);
        }
        Arrays.sort(randomIncrements);
        DateTime[] dates = new DateTime[numberOfDates];
        for(int i=0; i<numberOfDates; i++){
            dates[i] = new DateTime(startDate.getMillis() + (randomIncrements[i]*(1000*60*minuteIncrements)));
        }
        return dates;
    }
    
    public static LinkedList<Skill> getRandomSkills(){
        
        LinkedList<Skill> allSkills = new LinkedList<Skill>(Config.getSkills());
        LinkedList<Skill> returnedSkills = new LinkedList<Skill>();
        int numberOfSkills = 1 + randomGenerator.nextInt(allSkills.size()-1);
        for(int i=0; i < numberOfSkills; i++){
            returnedSkills.add(allSkills.remove(randomGenerator.nextInt(allSkills.size())));
        }
        return returnedSkills;
    }
    
    public static void assignRandomShifts(LinkedList<Shift> shifts, LinkedList<StaffMember> staffMembers){
        for(Shift shift : shifts)
            shift.randomAssignStaffMember();
    }
    
    public static StaffMember getRandomStaffMember(LinkedList<StaffMember> staffMembers){
        return staffMembers.get(randomGenerator.nextInt(staffMembers.size()));
    }
    
    public static int getStaffMembersTotalAssignedMinutes(LinkedList<Shift> allShifts, StaffMember staffMember){
        LinkedList<Shift> assignedShifts = getStaffsAssignedShifts(allShifts, staffMember);
//        System.out.println("assigned shift size: "+assignedShifts.size());
        int totalNoOfMinutes = 0;
        for(Shift shift:assignedShifts){
//            System.out.println(shift.getShiftTimes().getStart().toString(Config.dateFormat)+"   "+shift.getShiftTimes().getEnd().toString(Config.dateFormat));
//            System.out.println("Shift duration in mins: "+shift.getShiftTimes().toDuration().getStandardMinutes());
            totalNoOfMinutes += shift.getShiftTimes().toDuration().getStandardMinutes();
        }
        return totalNoOfMinutes;
    }
    
    public static int getAssignedMinutesForDay(LinkedList<Shift> allShifts, StaffMember staffMember, DateTime day){
        DateTime startDate = day.dayOfWeek().roundFloorCopy();
        Interval dayOfTheWeek = new Interval(startDate, startDate.plusHours(24));
        LinkedList<Shift> assignedShifts = getStaffsAssignedShifts(allShifts, staffMember);
        int overlapOfShiftAndDayMinutes = 0;
        for(Shift shift:assignedShifts){
//            System.out.println(dayOfTheWeek.getStart().toString(Config.dateFormat));
//            System.out.println(dayOfTheWeek.overlap(shift.getShiftTimes()));
            if(dayOfTheWeek.overlap(shift.getShiftTimes()) != null)
                overlapOfShiftAndDayMinutes += dayOfTheWeek.overlap(shift.getShiftTimes()).toDuration().getStandardMinutes();
        }
        return overlapOfShiftAndDayMinutes;
    }
    
    public static LinkedList<Shift> getStaffsAssignedShifts(LinkedList<Shift> shifts, StaffMember staffMember){
        LinkedList<Shift> assignedShifts = new LinkedList<Shift>();
        for(Shift shift:shifts){
            if(shift.getAssignedStaffMember() == staffMember)
                assignedShifts.add(shift);
        }
        return assignedShifts;
    }
    
    public static LinkedList<StaffMember> copyLinkedList(LinkedList<StaffMember> staffMembers){
        LinkedList<StaffMember> copyStaffMembers = new LinkedList<StaffMember>();
        for(StaffMember staffMember:staffMembers)
            copyStaffMembers.add(staffMember);
        return copyStaffMembers;
    }
    
    public static void LocalSearch(WeekSchedule weekSchedule, int iterationMax){
        System.out.println("Beginning local search...");
//        weekSchedule.updateAllUnaryConstraints();
        for(int j=0;j<1;j++){
            weekSchedule.randomAssignAllShiftsStaffMember();
            for(int i=0; i<iterationMax; i++){
                if(ConstraintHandler.totalConstraintViolations(weekSchedule) == 0 && areAllShiftsAssigned(weekSchedule.getShifts())){
                    System.out.println("A solution has been reached");
                    break;
                }
                for(Shift shift:weekSchedule.getShifts()){
                    AssignToLeastConstraintViolation(shift, weekSchedule);
                }
                System.out.println("Iterations: "+i+"      Violations: "+ConstraintHandler.totalConstraintViolations(weekSchedule));
            }
        }
        System.out.println("Localsearch finished");
    }
    
    public static void AssignToLeastConstraintViolation(Shift shift, WeekSchedule weekSchedule){
        int bestConstaintViolations = 200000000;
        LinkedList<StaffMember> bestAssignment = new LinkedList<StaffMember>();
        for(StaffMember staffMember:shift.getDomainOfStaffMembers()){
            shift.setAssignedStaffMember(staffMember);
//            System.out.println("*****Assigning Begin*****");
//            shift.printShiftDetail();
//            System.out.println("THE VIOLATIONS"+ConstraintHandler.totalConstraintViolations(weekSchedule));
//            System.out.println("*****Assigning End*****");
            int newConstraintViolations = ConstraintHandler.totalConstraintViolations(weekSchedule);
            if(newConstraintViolations < bestConstaintViolations){
                bestAssignment.clear();
                bestAssignment.add(staffMember);
                bestConstaintViolations = newConstraintViolations;
            }else if(newConstraintViolations == bestConstaintViolations)
                bestAssignment.add(staffMember);
        }
        if(bestAssignment.size()>1){
            shift.setAssignedStaffMember(getRandomStaffMember(bestAssignment));
        }else if(bestAssignment.size()==1)
            shift.setAssignedStaffMember(bestAssignment.getFirst());
        else
            System.out.println("No Staff in Shifts Domain!!!\n");
    }
    
    public static boolean areAllShiftsAssigned(LinkedList<Shift> shifts){
        boolean allShiftsAssigned = true;
        for(Shift shift:shifts){
            if(shift.getAssignedStaffMember() == null){
                allShiftsAssigned = false;
                break;
            }
        }
        return allShiftsAssigned;
    }
    
    public static void printAllShiftsDetail(LinkedList<Shift> shifts){
        for(Shift shift:shifts)
            printShiftDetail(shift);
    }
    
    public static void printShiftDetail(Shift shift){
        System.out.println("\n******************SHIFT "
                +shift.getShiftTimes().getStart().toString(Config.dateFormat)+"******************");
        System.out.println("Start Time "+shift.getShiftTimes().getStart().toString(Config.dateFormat)
                +"      End Time "+shift.getShiftTimes().getEnd().toString(Config.dateFormat));
        System.out.println("Skills Needed");
        for(int j=0; j<shift.getSkillsNeeded().size(); j++)
            System.out.println(" -"+shift.getSkillsNeeded().get(j).getName());
        System.out.println("Domain of Staff");
        for(StaffMember staffMember:shift.getDomainOfStaffMembers())
            System.out.println(staffMember.getName()+"     ");
        if(shift.getAssignedStaffMember() != null)
            System.out.println("\nAssigned Staff Member \n"+shift.getAssignedStaffMember().getName());
    }
    
    public static void printAllStaffDetail(WeekSchedule weekSchedule, LinkedList<StaffMember> staffMembers){
        for(StaffMember staffMember:staffMembers)
            printStaffDetail(weekSchedule, staffMember);
    }
    
    public static void printStaffDetail(WeekSchedule weekSchedule, StaffMember staffMember){
        System.out.println("\n******************STAFF MEMBER "+staffMember.getName()+"******************");
        System.out.println("Available Hours");
        for(int j=0; j<staffMember.getAvailableIntervals().size(); j++)
            System.out.println(
                    staffMember.getAvailableIntervals().get(j).getStart().toString(Config.dateFormat)
                    +"  To  "
                    +staffMember.getAvailableIntervals().get(j).getEnd().toString(Config.dateFormat));
        System.out.println("Max Hours Per Week\n"+staffMember.getMaxHoursPerWeek());
        System.out.println("Possessed Skills");
        for(int j=0; j<staffMember.getPossessedSkills().size(); j++)
            System.out.println(" -"+staffMember.getPossessedSkills().get(j).getName());
        System.out.println("Assigned Shifts");
        for(Shift shift:UsefulMethods.getStaffsAssignedShifts(weekSchedule.getShifts(), staffMember))
            System.out.println("  "+shift.getShiftTimes().getStart().toString(Config.dateFormat)
                    +"  to  "+shift.getShiftTimes().getEnd().toString(Config.dateFormat));
    }
}
