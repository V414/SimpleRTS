/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.System;

/**
 *
 * @author Jack
 */
public class Clock {
    
    protected long mStartTime;
    
    public Clock() {
        mStartTime = System.currentTimeMillis();
    }
    
    public Time getElapsedTime() {
        return new Time(System.currentTimeMillis() - mStartTime);
    }
    
    public Time restart() {
        Time timePassed = getElapsedTime();
        mStartTime = System.currentTimeMillis();
        return timePassed;
    }
    
}
