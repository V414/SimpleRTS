/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.system;

/**
 *
 * @author Jack
 */
public class Time {
    
    protected long mMilliseconds;
    
    public Time() {
        mMilliseconds = 0;
    }
    
    public Time(long time) {
        mMilliseconds = time;
    }
    
    public float asSeconds() {
        return (float)mMilliseconds / 1000.f;
    }
    
    public long asMilliseconds() {
        return mMilliseconds;
    }
    
    public static Time milliseconds(int milliseconds) {
        return new Time(milliseconds);
    }
    
    public static Time seconds(float seconds) {
        return new Time((int)(seconds * 1000.f));
    }
    
    public static Time minutes(float minutes) {
        return seconds(minutes * 60.f);
    }
    
    public static Time hours(float hours) {
        return minutes(hours * 60.f);
    }
    
    public static Time days(float days) {
        return hours(days * 24.f);
    }
    
}
