/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.behaviours;

import com.dinasgames.main.World;
import com.dinasgames.main.math.Point;
import com.dinasgames.main.objects.GameObject;
import com.dinasgames.main.objects.entities.units.Unit;
import com.dinasgames.main.system.Time;
import com.dinasgames.net.StateValue;

/**
 *
 * @author Jack
 */
public class BMoveTo extends Behaviour {
    
    public static int ID = 1;
    
    // This behaviour will move the target from its current position to the given one over time.
    // The time will be calculated using the speed of the object if available.
    
    protected StateValue x,y;
    
    /**
     * Default constructor.
     * @param self
     * @param targetX
     * @param targetY
     */
    public BMoveTo( Object self, float targetX, float targetY ) {
        
        super(self);
        
        if(self != null && self instanceof Unit) {
            
            Unit unit = (Unit)self;
            
            float currentX  = unit.getX();
            float currentY  = unit.getY();
            float speed     = unit.getSpeed(); // Meters per second
            float distance  = (float)Point.distance( currentX, currentY, targetX, targetY );
            float timeSeconds = ( World.pixelsToMeters(distance) / speed );
            
            int timeMilliseconds = (int)(timeSeconds * 1000.f);
            
            unit.setRotation(-(float)Point.direction( currentX, currentY, targetX, targetY ));
            
            // Create a state with our current values
            x = new StateValue( currentX );
            y = new StateValue( currentY );
            
            // Plot a point in the future with our calculatedTime
            x.add(targetX, timeMilliseconds);
            y.add(targetY, timeMilliseconds);
            
        }
        
    }
    
    @Override
    public int getUniqueID() {
        return ID;
    }
    
    @Override
    public void update(Time timePassed) {
        
        if(x == null || y == null) {
            return;
        }
        
        if(self instanceof GameObject) {
            ((GameObject)self).setPosition(x.getValue(), y.getValue());
        }
        
    }
    
}
