/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.behaviours;

import com.dinasgames.engine.math.Point;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.engine.pathfinding.Path;
import com.dinasgames.engine.pathfinding.Path.Step;
import com.dinasgames.engine.system.Time;
import com.dinasgames.main.World;
import com.dinasgames.main.objects.GameObject;
import com.dinasgames.main.objects.entities.units.Unit;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.net.StateValue;

/**
 *
 * @author Jack
 */
public class BPathFindTo extends Behaviour {
    
    public static int ID = 5;
    
    // This behaviour will move the target from its current position to the given one using A Star Path Finding.
    
    public static class Data {
      
      public Scene scene;
      public Unit unit;
      
      public Data( Scene scene, Unit unit ) {
        this.scene = scene;
        this.unit = unit;
      }
      
    }
    
    protected BMoveTo moveTo;
    protected Data data;
    protected Path path;
    protected int currentPoint;
    
    /**
     * Default constructor.
     * @param self
     * @param targetX
     * @param targetY
     */
    public BPathFindTo( Data self, float targetX, float targetY ) {
        
        super(self);
        
        if(self != null && self instanceof Data) {
            
          // Use this unit
          data = (Data)self;
          
          // Calculate the path
          path = data.unit.findPath(data.scene, targetX, targetY);
            
        }
        
    }
    
    @Override
    public int getUniqueID() {
        return ID;
    }
    
    @Override
    public void update(Time timePassed) {
        
      // If we calculated a path
      if(path != null) {
        
        // Get the current step
        Step currentStep = path.getStep( currentPoint );
        
        // Get our current position
        Vector2f currentPosition = data.unit.getPosition();
        
        if( moveTo == null ) {
          
          // We haven't got a Move To behaviour. So make one
          if(currentPoint >= path.getLength() - 1) {
            // We have arrived at the end point, clear the path.
            path = null;
            return;
          }
          
          // Move to the next point
          moveTo = new BMoveTo( data.unit, currentStep.getX(), currentStep.getY() );
          
        }else{
          
          // Update the movement of this unit
          moveTo.update(timePassed);
          
          // We have a Move To, check if we have reached the current point
          if(Point.distance(currentPosition.x, currentPosition.y, currentStep.getX(), currentStep.getY()) < 10.f) {
            // We have reached this point, goto the next
            moveTo = null;
            currentPoint++;
          }
          
        }
        
      }
        
    }
    
}