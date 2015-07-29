/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.net;

import com.dinasgames.engine.system.Clock;
import com.dinasgames.engine.system.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jack
 */
public class StateValue {
    
    public class Point {
        
        public int     time;
        public float   value;
        
        public Point( float value, int time ) {
            this.value = value;
            this.time = time;
        }
        
        public Point( Point other ) {
            this.value = other.value;
            this.time = other.time;
        }
        
    }
    
    protected Clock         clock;
    protected List<Point>   points;
    protected Point         currentPoint;
    protected int           lastAccess;
    
    public StateValue() {
        clock = new Clock();
        points = new ArrayList();
        currentPoint = new Point( 0.f, 0 );
        lastAccess = -1;
    }
    
    public StateValue(float value) {
        this();
        currentPoint.value = value;
    }
    
    public int getCurrentTime() {
        return (int)clock.getElapsedTime().asMilliseconds();
    }
    
    public float getValue( int time ) {
        
        return interpolate( time, false ).value;
        
    }
    
    public float getValue() {
        
        int ct = getCurrentTime();
        
        if(lastAccess != ct ) {
            currentPoint = interpolate( ct, true );
            lastAccess = ct;
        }
        
        return currentPoint.value;
        
    }
    
    public void add( float value, int time ) {
        
        if(time < getCurrentTime()) {
            return;
        }
        
        points.add(new Point(value,time));
        
    }
    
    protected Point interpolate( int currentTime, boolean removeOldData ) {
        
        Point point = new Point(this.currentPoint);
        
        if(points.size() < 1) {
            return point;
        }
        
        Point nextPoint = null;
        Iterator<Point> it = points.iterator();
        while(it.hasNext()) {
            
            nextPoint = it.next();
            
            if(nextPoint.time >= currentTime) {
                break;
            }
            
            // Ensure the current point is up to date
            point.value = nextPoint.value;
            point.time = nextPoint.time;
            
            // Remove the old point
            if(removeOldData) {
                it.remove();
            }
            
        }
        
        if(nextPoint == null) {
            return point;
        }
        
        // Interpolate
        float dt = Math.max( 0.f, Math.min( 1.f, ((float)(currentTime - point.time) / (float)(nextPoint.time - point.time))  ) );
        
        if(Float.isNaN(dt)) {
            dt = 0.f;
        }
        
        return new Point( point.value + (( nextPoint.value - point.value ) * dt), currentTime );
        
        //System.out.println( (currentTime - currentPoint.time) + " of " + (nextPoint.time - currentPoint.time) + " delta: " + dt );
        
    }
    
}
