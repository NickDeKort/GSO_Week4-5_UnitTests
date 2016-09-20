/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fontys.time.ITime;
import fontys.time.ITimeSpan;
import fontys.time.Time;
import fontys.time.TimeSpan;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Nick de Kort, Martijn Broekman
 */
public class TimeSpanTest {

    TimeSpan per;
    Time t1;

    ITime beforeStart;
    ITime start;
    ITime middleFirst;
    ITime middleLast;
    ITime end;
    ITime afterEnd;
    ITime afterAfterEnd;
    
    ITimeSpan timespanP;
    ITimeSpan timespanA;
    ITimeSpan timespanB;
    ITimeSpan timespanC;
    ITimeSpan timespanD;
    ITimeSpan timespanE;
    ITimeSpan timespanF;
    
    public TimeSpanTest() {
        //
    }

    @Before
    public void setUpClass() {
        t1 = new Time(2013, 04, 05, 18, 30);
        Time t2 = new Time(2013, 04, 05, 19, 0);
        per = new TimeSpan(t1, t2);
        
        beforeStart   = new Time(2014, 3, 1, 0, 0);
        start         = new Time(2014, 3, 1, 1, 0);
        middleFirst   = new Time(2014, 3, 1, 2, 0);
        middleLast    = new Time(2014, 3, 1, 3, 0);
        end           = new Time(2014, 3, 1, 4, 0);
        afterEnd      = new Time(2014, 3, 1, 5, 0);
        afterAfterEnd = new Time(2014, 3, 1, 6, 0);
        
        timespanP = new TimeSpan(start, end);
        timespanA = new TimeSpan(beforeStart, start);
        timespanB = new TimeSpan(beforeStart, middleFirst);
        timespanC = new TimeSpan(middleFirst, middleLast);
        timespanD = new TimeSpan(beforeStart, afterEnd);
        timespanE = new TimeSpan(end, afterEnd);
        timespanF = new TimeSpan(afterEnd, afterAfterEnd);
    }

    @Test
    public void testIsPartOf() {
        Time t1 = new Time(2013, 04, 05, 18, 40);
        Time t2 = new Time(2015, 06, 06, 18, 40);
        TimeSpan ts1 = new TimeSpan(t1, t2);
        
        Time t3 = new Time(2013, 05, 05, 18, 40);
        Time t4 = new Time(2013, 05, 06, 18, 40);
        TimeSpan ts2 = new TimeSpan(t3, t4);
        
        Assert.assertTrue("timespan 2 is not part of container timespan", ts2.isPartOf(ts1));
    }

    @Test
    public void testSetEndTime() {
        Time t2 = new Time(2013, 04, 05, 18, 40);
        TimeSpan timespan = new TimeSpan(t1, t2);
        Time t3 = new Time(2013, 05, 06, 18, 40);
        timespan.setEndTime(t3);
        Assert.assertEquals("time parameter 1 not saved", timespan.getEndTime(), t3);
    }

    @Test
    public void testSetBeginTime() {
        Time t2 = new Time(2013, 05, 05, 18, 30);
        TimeSpan timespan = new TimeSpan(t1, t2);
        Time t3 = new Time(2013, 05, 03, 18, 40);
        timespan.setBeginTime(t3);
        Assert.assertEquals("time parameter 1 not saved", timespan.getBeginTime(), t3);
    }

    @Test
    public void newPeriode() {
        Assert.assertNotNull("time parameter 1 not saved", per.getBeginTime());
        Assert.assertNotNull("time parameter 1 not saved", per.getEndTime());
        Assert.assertEquals(-30, per.length());
        t1.plus(10);
        per.setBeginTime(t1);
        Assert.assertEquals(t1, per.getBeginTime());
        
        Assert.assertEquals("Something basic is wrong in creating periods or times", timespanP.getBeginTime().getHours(), start.getHours());
        Assert.assertEquals("Something basic is wrong in creating periods or times", timespanB.getBeginTime().getHours(), beforeStart.getHours());
    }

    @Test
    public void ComparePeriodes() {
        Time t2 = new Time(2013, 04, 05, 18, 40);
        try {
            TimeSpan shouldfail = new TimeSpan(t2, t1);
            Assert.assertTrue("Period end lays before start; This code should not be reached", false);
        } catch (IllegalArgumentException e) {
            // Ok!
        }
        
        TimeSpan p = new TimeSpan(t1, t2);
        Assert.assertEquals(p.getBeginTime(), per.intersectionWith(p).getBeginTime());
        Assert.assertEquals(p.getEndTime(), per.intersectionWith(p).getEndTime());
        p.move(5);
        Assert.assertEquals(45, p.getEndTime().getMinutes());
        p.changeLengthWith(5);
        Assert.assertEquals(50, p.getEndTime().getMinutes());
        //Assert.assertTrue(40, per.isPartOf(per2));
    }

    @Test
    public void testIntersection() {
        Assert.assertEquals("There is no overlap between periods P and A.", null, timespanP.intersectionWith(timespanA));
        Assert.assertEquals("The intersection between periods P and B should be P.begin through B.end", timespanP.intersectionWith(timespanB).getBeginTime().getHours(), timespanP.getBeginTime().getHours());
        Assert.assertEquals("The intersection between periods P and B should be P.begin through B.end", timespanP.intersectionWith(timespanB).getEndTime(), timespanB.getEndTime());
        Assert.assertEquals("The intersection between periods P and C should be equal to C", timespanP.intersectionWith(timespanC).getBeginTime(), timespanC.getBeginTime());
        Assert.assertEquals("The intersection between periods P and C should be equal to C", timespanP.intersectionWith(timespanC).getEndTime(), timespanC.getEndTime());
        Assert.assertEquals("The intersection between periods P and D should be equal to P", timespanP.intersectionWith(timespanD).getBeginTime().getHours(), timespanP.getBeginTime().getHours());
        Assert.assertEquals("The intersection between periods P and D should be equal to P", timespanP.intersectionWith(timespanD).getEndTime().getHours(), timespanP.getEndTime().getHours());
        Assert.assertEquals("There is no overlap between periods P and E", timespanP.intersectionWith(timespanE), null);
        Assert.assertEquals("There is no overlap between periods P and F", timespanF.intersectionWith(timespanP), null);
    }

    @Test
    public void testUnion() {
        Assert.assertEquals("Union of periods P and A should start at A.begin", timespanP.unionWith(timespanA).getBeginTime().getHours(), timespanA.getBeginTime().getHours());
        Assert.assertEquals("Union of periods P and A should end at P.end", timespanP.unionWith(timespanA).getEndTime().getHours(), timespanP.getEndTime().getHours());
        Assert.assertEquals("Union of periods P and B should start at B.begin", timespanP.unionWith(timespanB).getBeginTime().getHours(), timespanB.getBeginTime().getHours());
        Assert.assertEquals("Union of periods P and B should end at P.end", timespanP.unionWith(timespanB).getEndTime().getHours(), timespanP.getEndTime().getHours());
        Assert.assertEquals("Union of periods P and C should start at P.begin", timespanP.unionWith(timespanC).getBeginTime().getHours(), timespanP.getBeginTime().getHours());
        Assert.assertEquals("Union of periods P and C should end at P.end", timespanP.unionWith(timespanC).getEndTime().getHours(), timespanP.getEndTime().getHours());
        Assert.assertEquals("Union of periods P and D should start at D.begin", timespanP.unionWith(timespanD).getBeginTime().getHours(), timespanD.getBeginTime().getHours());
        Assert.assertEquals("Union of periods P and D should end at D.end", timespanP.unionWith(timespanD).getEndTime().getHours(), timespanD.getEndTime().getHours());
        Assert.assertEquals("Union of periods P and E should start at P.begin", timespanP.unionWith(timespanE).getBeginTime().getHours(), timespanP.getBeginTime().getHours());
        Assert.assertEquals("Union of periods P and E should end at E.end", timespanP.unionWith(timespanE).getEndTime().getHours(), timespanE.getEndTime().getHours());
        Assert.assertEquals("Union of periods P and F should be null", timespanP.unionWith(timespanF), null);
    }
}
