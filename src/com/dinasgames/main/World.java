/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main;

/**
 *
 * @author Jack
 */
public class World {
    
    /**
     * How many pixels are considered a meter.
     */
    public static final float PPM = 10.f;
    
    
    
    /**
     * Meters in a mile.
     */
    public static final float MILE = 1609.344f;
    
    /**
     * Convert miles per hour into meters per second.
     * @return 
     */
    public static float MPH( float mph ) {
        //return ((mph / 60.f) / 1000.f);
        return ((mph * 1609.344f) / 3600.f);
    }
    
    /**
     * Convert meters into pixels.
     * @param m
     * @return 
     */
    public static float metersToPixels(float m) {
        return m * PPM;
    }
    
    /**
     * Convert pixels to meters.
     * @param p
     * @return 
     */
    public static float pixelsToMeters(float p) {
        return p / PPM;
    }
    
}
