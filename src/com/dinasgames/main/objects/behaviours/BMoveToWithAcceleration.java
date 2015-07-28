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

/**
 *
 * @author Jack
 */
public class BMoveToWithAcceleration extends Behaviour {
    
    
    public static int ID = 2;
    
    // This behaviour will move the target from its current position to the given one over time.
    // The time will be calculated using the speed, acceleration and deceleration of the object if available.
    
    protected float speed, acceleration, deceleration, maxSpeed, tx, ty;
    
    /**
     * Default constructor.
     * @param self
     * @param targetX
     * @param targetY
     */
    public BMoveToWithAcceleration( Object self, float targetX, float targetY ) {
        
        super(self);
        
        this.speed  = 0.f;
        this.tx     = targetX;
        this.ty     = targetY;
        
        if(self != null && self instanceof Unit) {
            
            Unit e = (Unit)self;
            
            this.acceleration   = e.getAcceleration();
            this.deceleration   = e.getDeceleration();
            this.maxSpeed       = e.getSpeed();
            
        }
        
    }
    
    @Override
    public int getUniqueID() {
        return ID;
    }
    
    @Override
    public void update(Time timePassed) {
        
        if(self instanceof GameObject) {
            
            GameObject o = (GameObject)self;
            
            float x, y, dt, direction, stopDistance;
            x = o.getX();
            y = o.getY();
            dt = timePassed.asSeconds();
            direction = (float)Point.direction( x,y,tx,ty );
            stopDistance = World.metersToPixels(speed * (speed / deceleration));
            
            if(Point.distance(x,y,tx,ty) < stopDistance) {
                speed -= World.metersToPixels(deceleration) * dt;
                speed = Math.max( 0.f, speed );
            }else{
                speed += World.metersToPixels(acceleration) * dt;
                speed = Math.min( speed, World.metersToPixels(maxSpeed) );
            }
            
            x += Point.inDirectionX( speed, direction );
            y += Point.inDirectionY( speed, direction );

            o.setPosition(x, y);
            
        }
        
    }
    
    
}
