/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Frank Peeters, Nico Kuijpers
 * 
 * LET OP: De klasse Time bevat enkele fouten.
 * 
 */
public class Time implements ITime {

    private int year = 0;
    private int month = 0;
    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    /**
     * creation of a Time-object with year y, month m, day d, hours h and
     * minutes m; if the combination of y-m-d refers to a non-existing date a
     * correct value of this Time-object will be not guaranteed
     *
     * @param y
     * @param m 1≤m≤12
     * @param d 1≤d≤31
     * @param h 0≤h≤23
     * @param min 0≤min≤59
     */
    public Time(int y, int m, int d, int h, int min) {
        // Set year
        this.year = y;

        // Check if month is correct
        if (m < 1 || m > 12) {
            throw new IllegalArgumentException();
        } else {
            this.month = m;
        }

        // Check if day is correct
        if (d < 1 || d > 31) {
            throw new IllegalArgumentException();
        } else {
            this.day = d;
        }

        // Check if hour is correct
        if (h < 0 || h > 23) {
            throw new IllegalArgumentException();
        } else {
            this.hour = h;
        }

        // Check if minutes is correct
        if (min < 0 || min > 59) {
            throw new IllegalArgumentException();
        } else {
            this.minute = min;
        }
    }

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public int getMonth() {
        return this.month;
    }

    @Override
    public int getDay() {
        return this.day;
    }

    @Override
    public int getHours() {
        return this.hour;
    }

    @Override
    public int getMinutes() {
        return this.minute;
    }

    @Override
    public DayInWeek getDayInWeek() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(this.year, this.month, this.day, this.hour, this.minute);
        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        return DayInWeek.values()[dow];
    }

    @Override
    public ITime plus(int minutes) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(this.year, this.month, this.day, this.hour, this.minute);
        calendar.add(Calendar.MINUTE, minutes);

        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR);
        this.minute = calendar.get(Calendar.MINUTE);
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);

        return this;
    }

    /**
     * @return difference in minutes
     */
    @Override
    public int difference(ITime time) {
        Calendar dateOne = GregorianCalendar.getInstance();
        dateOne.set(time.getYear(), time.getMonth(), time.getDay(), time.getHours(), time.getMinutes());

        Calendar dateSecond = Calendar.getInstance();
        dateSecond.set(this.year, this.month, this.day, this.hour, this.minute);

        long t1 = dateOne.getTimeInMillis();
        long t2 = dateSecond.getTimeInMillis();
        long diffInMilliSecond = t2 - t1;

        return (int) Math.floor(diffInMilliSecond / (1000 * 60));
    }

    @Override
    public int compareTo(ITime o) {
        final int EQUAL = 0;
        final int UNEQUAL = 1;

        if (this.getYear() != o.getYear()) {
            return UNEQUAL;
        }

        if (this.getMonth() != o.getMonth()) {
            return UNEQUAL;
        }

        if (this.getDay() != o.getDay()) {
            return UNEQUAL;
        }

        if (this.getHours() != o.getHours()) {
            return UNEQUAL;
        }

        if (this.getMinutes() != o.getMinutes()) {
            return UNEQUAL;
        }

        return EQUAL;
    }
}
