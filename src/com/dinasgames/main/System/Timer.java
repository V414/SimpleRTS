/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.System;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jack
 */
public class Timer {
    
    public interface Function {
        
        public void run();
        
    };
    
    protected static List<Timer> timers = new ArrayList();
    
    public static Timer after(Time time, Function f) {
        Timer t = new Timer(f, time, false);
        timers.add(t);
        return t;
    }
    
    public static Timer every(Time time, Function f) {
        Timer t = new Timer(f, time, true);
        timers.add(t);
        return t;
    }
    
    public static void update() {
        synchronized(timers) {
            Iterator<Timer> it = timers.iterator();
            while(it.hasNext()) {
                Timer timer = it.next();
                if(timer.isReady()) {
                    if(timer.isRepeated()) {
                        timer.run().restart();
                    }else{
                        timer.run().stop();
                    }
                }
            }
        }
    }
    
    protected Time mTime;
    protected Clock mClock;
    protected boolean mRepeat;
    protected Function mFunction;
    
    protected Timer(Function f, Time time, boolean repeat) {
        mFunction = f;
        mRepeat = repeat;
        mTime = time;
        mClock = new Clock();
    }
    
    public Timer run() {
        mFunction.run();
        return this;
    }
    
    public boolean isRepeated() {
        return mRepeat;
    }
    
    public Function getFunction() {
        return mFunction;
    }
    
    public boolean isReady() {
        return (mClock.getElapsedTime().asMilliseconds() >= mTime.asMilliseconds());
    }
    
    public Timer restart() {
        mClock.restart();
        return this;
    }
    
    public Timer stop() {
        timers.remove(this);
        return this;
    }
    
}
