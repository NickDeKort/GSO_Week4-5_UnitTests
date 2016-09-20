/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Frank Peeters, Nico Kuijpers, Nick de Kort
 * 
 * LET OP: De klasse TimeSpan bevat enkele fouten.
 * 
 */
public class TimeSpan implements ITimeSpan {

    /* class invariant: 
     * A stretch of time with a begin time and end time.
     * The end time is always later then the begin time; the length of the time span is
     * always positive
     * 
     */
    private ITime bt, et;
    
    /**
     * 
     * @param bt must be earlier than et
     * @param et 
     */
    public TimeSpan(ITime bt, ITime et) {
        if (et.difference(bt) < 0) {
            throw new IllegalArgumentException("begin time "
                    + bt + " must be earlier than end time " + et);
        }

        this.bt = bt;
        this.et = et;
    }

    @Override
    public ITime getBeginTime() {
        return bt;
    }

    @Override
    public ITime getEndTime() {
        return et;
    }

    @Override
    public int length() {
        return bt.difference(et);
    }

    @Override
    public void setBeginTime(ITime beginTime) {
        if (beginTime.difference(this.bt) > 0) {
            this.bt = beginTime;
        }
    }

    @Override
    public void setEndTime(ITime endTime) {
        if (endTime.difference(bt) > 0) {
            et = endTime;
        }
    }

    @Override
    public void move(int minutes) {
        bt.plus(minutes);
        et.plus(minutes);
    }

    @Override
    public void changeLengthWith(int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("length of period must be positive");
        }
        
        et = et.plus(minutes);
    }

    @Override
    public boolean isPartOf(ITimeSpan timeSpan) {
        return this.unionWith(timeSpan) != null;
    }

    @Override
    public ITimeSpan unionWith(ITimeSpan timeSpan) {
        // Does $period's begin time align with $this endtime?
        if (timeSpan.getBeginTime() == this.et) {
            return new TimeSpan(this.bt, timeSpan.getEndTime());
        }
        
        // Or the other way around: $this' begintime match with $period's endtime?
        if (this.bt == timeSpan.getEndTime()) {
            return new TimeSpan(timeSpan.getBeginTime(), this.et);
        }
        
        // Too bad, the periods do not align nicely. Check whether there is overlap at all...
        if (this.intersectionWith(timeSpan) != null) {
            // Copied this code from intersectionWith(), except that here we'll check for the longest possible overlap instead of the shortest
            Calendar dateOne = GregorianCalendar.getInstance();
            dateOne.set(timeSpan.getBeginTime().getYear(), timeSpan.getBeginTime().getMonth(), timeSpan.getBeginTime().getDay(), timeSpan.getBeginTime().getHours(), timeSpan.getBeginTime().getMinutes());

            Calendar dateTwo = GregorianCalendar.getInstance();
            dateTwo.set(timeSpan.getEndTime().getYear(), timeSpan.getEndTime().getMonth(), timeSpan.getEndTime().getDay(), timeSpan.getEndTime().getHours(), timeSpan.getEndTime().getMinutes());

            Calendar dateThree = Calendar.getInstance();
            dateThree.set(this.bt.getYear(), this.bt.getMonth(), this.bt.getDay(), this.bt.getHours(), this.bt.getMinutes());

            Calendar dateFour = Calendar.getInstance();
            dateFour.set(this.et.getYear(), this.et.getMonth(), this.et.getDay(), this.et.getHours(), this.et.getMinutes());
            
            ITime begin;
            if (dateOne.getTimeInMillis() <= dateThree.getTimeInMillis()) // if $period starts before $this starts
                begin = timeSpan.getBeginTime();
            else
                begin = this.getBeginTime();

            ITime end;
            if (dateFour.getTimeInMillis() <= dateTwo.getTimeInMillis()) // if $this ends before $period ends
                end = timeSpan.getEndTime();
            else
                end = this.getEndTime();

            return new TimeSpan(begin, end);
        }
        
        return null;
    }

    @Override
    public ITimeSpan intersectionWith(ITimeSpan timeSpan) {
        Calendar dateOne = GregorianCalendar.getInstance();
        dateOne.set(timeSpan.getBeginTime().getYear(), timeSpan.getBeginTime().getMonth(), timeSpan.getBeginTime().getDay(), timeSpan.getBeginTime().getHours(), timeSpan.getBeginTime().getMinutes());

        Calendar dateTwo = GregorianCalendar.getInstance();
        dateTwo.set(timeSpan.getEndTime().getYear(), timeSpan.getEndTime().getMonth(), timeSpan.getEndTime().getDay(), timeSpan.getEndTime().getHours(), timeSpan.getEndTime().getMinutes());

        Calendar dateThree = Calendar.getInstance();
        dateThree.set(this.bt.getYear(), this.bt.getMonth(), this.bt.getDay(), this.bt.getHours(), this.bt.getMinutes());

        Calendar dateFour = Calendar.getInstance();
        dateFour.set(this.et.getYear(), this.et.getMonth(), this.et.getDay(), this.et.getHours(), this.et.getMinutes());
        
        if (dateFour.getTimeInMillis() <= dateOne.getTimeInMillis())
            return null; // $this ends before or at the beginning of $period (no overlap)
        if (dateThree.getTimeInMillis() >= dateTwo.getTimeInMillis())
            return null; // $this starts after or at the end of $period (no overlap)
        
        ITime begin;
        if (dateOne.getTimeInMillis() <= dateThree.getTimeInMillis()) // if $period starts before $this starts
            begin = this.getBeginTime();
        else
            begin = timeSpan.getBeginTime();
        
        ITime end;
        if (dateFour.getTimeInMillis() <= dateTwo.getTimeInMillis()) // if $this ends before $period ends
            end = this.getEndTime();
        else
            end = timeSpan.getEndTime();
        
        return new TimeSpan(begin, end);
    }
}
