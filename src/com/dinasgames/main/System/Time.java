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
    
}
