/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.net;

import com.dinasgames.main.system.Clock;
import com.dinasgames.main.system.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jack
 */
public class Curve {
    
    public class CurvePoint {
        
        public int     time;
        public Object  value;
        
        public CurvePoint( Object value, int time ) {
            this.value = value;
            this.time = time;
        }
        
    }
    
    protected Clock             clock;
    protected List<CurvePoint>  points;
    protected CurveData         self;
    
    public Curve(CurveData self) {
        
        this.self = self;
        this.points = new ArrayList();
        this.clock = new Clock();
        
        add( self.getValue(), 0 );
        
    }

    public int getCurrentTime() {
        return (int)clock.getElapsedTime().asMilliseconds();
    }
    
    public Object getValue() {
        
        int currentTime = getCurrentTime();
        
        // Get rid of old data
        Iterator<CurvePoint> it = points.iterator();
        while(it.hasNext()) {
            
            if(points.size() <= 1) {
                return self.getValue();
            }
            
            CurvePoint point = it.next();
            
            if(point.time >= currentTime) {
                break;
            }
            
            self.setValue(point.value);
            it.remove();
            
        }
        
        if( size() <= 1 ) {
            return self.getValue();
        }
        
        return self.interpolate( points.get(0), points.get(1), currentTime );
//        
//        int currentTime = getCurrentTime();
//        CurvePoint start = new CurvePoint( self.getValue(), 0 );
//        CurvePoint end = null;
//        
//        Iterator<CurvePoint> it = points.iterator();
//        
//        // We have no future points. Use the current value
//        if(!it.hasNext()) {
//            return self.getValue();
//        }
//        
//        // Remove old data
//        while(it.hasNext()) {
//            
//            CurvePoint point = it.next();
//            
//            if(point.time < currentTime) {
//                //start.value = point.value;
//                it.remove();
//                continue;
//            }
//            
//            end = point;
//            
//            break;
//            
//        }
//        
//        if(start == null || end == null) {
//            return self.getValue();
//        }
//        
//        return self.interpolate( start, end, currentTime );
        
    }
    
    public void add(Object value, int time) {
        points.add(new CurvePoint( value, time ));
    }
    
    public void remove(int time) {
        for(int i = 0; i < points.size(); i++) {
            if(points.get(i).time == time) {
                points.remove(i);
                return;
            }
        }
    }
    
    public int size() {
        return points.size();
    }
    
    public boolean empty() {
        return size() == 0;
    }
    
}
