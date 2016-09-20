/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fontys.time.Time;
import fontys.time.DayInWeek;
import static org.hamcrest.core.Is.is;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/* *
 * @author Nick de Kort, Martijn Broekman
 */
public class TimeTest {

    public TimeTest() {
        Time time;

        try {
            time = new Time(2014, 12, 31, 23, 59);
        } catch (IllegalArgumentException ex) {
            fail("Time constructor did not allow legal month");
        }
        
        try {
            time = new Time(2014, 0, 31, 23, 59);
            fail("Time constructor allowed illegal month");
        } catch (IllegalArgumentException ex) {
            // Expected result received
        }

        try {
            time = new Time(2014, 12, 32, 23, 59);
            fail("Time constructor allowed illegal day");
        } catch (IllegalArgumentException ex) {
            // Expected result received
        }

        try {
            time = new Time(2014, 12, 31, 24, 59);
            fail("Time constructor allowed illegal hour");
        } catch (IllegalArgumentException ex) {
            // Expected result received
        }

        try {
            time = new Time(2014, 12, 31, 23, 60);
            fail("Time constructor allowed illegal minute");
        } catch (IllegalArgumentException ex) {
            // Expected result received
        }
    }

    /**
     * Test of getYear method, of class Time.
     */
    @org.junit.Test
    public void testGetYear() {
        System.out.println("getYear");

        Time time = new Time(2014, 12, 31, 23, 59);
        int expResult = 2014;
        int result = time.getYear();
        assertEquals("Expected year does not match returned year", expResult, result);
    }

    /**
     * Test of getMonth method, of class Time.
     */
    @org.junit.Test
    public void testGetMonth() {
        System.out.println("getMonth");

        Time time = new Time(2014, 12, 31, 23, 59);
        int expResult = 12;
        int result = time.getMonth();
        assertEquals("Expected month does not match returned month", expResult, result);
    }

    /**
     * Test of getDay method, of class Time.
     */
    @org.junit.Test
    public void testGetDay() {
        System.out.println("getDay");

        Time time = new Time(2014, 12, 31, 23, 59);
        int expResult = 31;
        int result = time.getDay();
        assertEquals("Expected day does not match returned day", expResult, result);
    }

    /**
     * Test of getHours method, of class Time.
     */
    @org.junit.Test
    public void testGetHours() {
        System.out.println("getHours");

        Time time = new Time(2014, 12, 31, 23, 59);
        int expResult = 23;
        int result = time.getHours();
        assertEquals("Expected hour does not match returned hour", expResult, result);
    }

    /**
     * Test of getMinutes method, of class Time.
     */
    @org.junit.Test
    public void testGetMinutes() {
        System.out.println("getMinutes");

        Time time = new Time(2014, 12, 31, 23, 59);
        int expResult = 59;
        int result = time.getMinutes();
        assertEquals("Expected minutes does not match returned minutes", expResult, result);
    }

    /**
     * Test of getDayInWeek method, of class Time.
     */
    @org.junit.Test
    public void testGetDayInWeek() {
        System.out.println("getDayInWeek");

        Time time = new Time(2014, 3, 21, 23, 59);
        DayInWeek expDay = DayInWeek.TUE;
        DayInWeek result = time.getDayInWeek();
        assertEquals("Assumed day in week does not match returned day in week", expDay, result);
    }

    /**
     * Test of plus method, of class Time.
     */
    @org.junit.Test
    public void testPlus() {
        System.out.println("plus");

        Time time = new Time(2014, 3, 21, 23, 30);
        Time newTime = (Time) time.plus(10);

        Assert.assertThat("Old time object same as new time object", newTime, IsEqual.equalTo(time));
        Assert.assertThat("Old time object minutes variable same as new time object minutes variable", time.getMinutes(), IsEqual.equalTo(time.getMinutes()));
    }

    /**
     * Test of difference method, of class Time.
     */
    @org.junit.Test
    public void testDifference() {
        System.out.println("difference");

        Time timeOne = new Time(2014, 3, 21, 23, 30);
        Time timeTwo = new Time(2014, 3, 21, 23, 10);
        Assert.assertThat("Difference between given dates should be 20 minutes", timeOne.difference(timeTwo), is(20));
    }

    /**
     * Test of compareTo method, of class Time.
     */
    @org.junit.Test
    public void testCompareTo() {
        System.out.println("compareTo");

        Time timeOne = new Time(2014, 3, 21, 23, 30);
        Time timeTwo = new Time(2014, 3, 21, 23, 30);
        Assert.assertThat("Time objects should be the same", timeOne.compareTo(timeTwo), is(0));
        
        
        timeOne = new Time(2014, 3, 21, 23, 30);
        timeTwo = new Time(2015, 3, 21, 23, 30);
        Assert.assertThat("Year in time objects should not be the same", timeOne.compareTo(timeTwo), is(0));
    }

}
