/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complexschedulingproblem;

import java.util.LinkedList;
import org.joda.time.DateTime;

/**
 *
 * @author seb
 */
public class Config {
    public static int timeInterval = 30;
    public static DateTime testStartDate = new DateTime(2012,10,01,00,00), testEndDate = new DateTime(2012,10,8,00,00);
    public static LinkedList<Skill> testSkills = Config.initSkills();
    public static String dateFormat = "yyyy/MM/dd HH:mm";
    
    private static LinkedList<Skill> initSkills(){
        LinkedList<Skill> tempSkills = new LinkedList<Skill>();
        tempSkills.add(new Skill("bar"));
        tempSkills.add(new Skill("cook"));
        tempSkills.add(new Skill("manager"));
        return tempSkills;
    }
    
    public static LinkedList<Skill> getSkills(){
//        LinkedList<Skill> tempSkills = new LinkedList<Skill>();
//        tempSkills.add(new Skill("bar"));
//        tempSkills.add(new Skill("cook"));
//        tempSkills.add(new Skill("manager"));
//        return tempSkills;
        return testSkills;
    }
}
