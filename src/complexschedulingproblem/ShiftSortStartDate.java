/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complexschedulingproblem;

import java.util.Comparator;
import org.joda.time.DateTime;

/**
 *
 * @author seb
 */
public class ShiftSortStartDate implements Comparator<Object>{

    @Override
    public int compare(Object t, Object t1) {
        DateTime shiftOne = ((Shift) t).getShiftTimes().getStart();
        DateTime shiftTwo = ((Shift) t1).getShiftTimes().getStart();
        
        int correctPosition;
        
        if(shiftOne.getMillis() > shiftTwo.getMillis())
            correctPosition = -1;
        else if(shiftOne.getMillis() == shiftTwo.getMillis())
            correctPosition = 0;
        else
            correctPosition = 1;
        
        return correctPosition;
    }
    
}
