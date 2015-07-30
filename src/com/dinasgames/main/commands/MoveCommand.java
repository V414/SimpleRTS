/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.commands;

import com.dinasgames.engine.math.Point;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.engine.system.Time;
import com.dinasgames.main.behaviours.BPathFindTo;
import com.dinasgames.main.behaviours.BPathFindTo.Data;
import com.dinasgames.main.objects.entities.units.Unit;
import com.dinasgames.main.scenes.Scene;

/**
 *
 * @author Jack
 */
public class MoveCommand extends Command {
    
    protected Scene scene;
    protected float x, y;
    
    public MoveCommand( Scene scene, float x, float y ) {
        this.x = x;
        this.y = y;
        this.scene = scene;
    }
    
    public MoveCommand( Vector2f pos ) {
        this.x = pos.x;
        this.y = pos.y;
    }
    
    @Override
    public void update(Time timePassed) {
      
      super.update(timePassed);
      
      if(mCompleted) {
        return;
      }
      
      // Check whether the entity has arrived at its target
      if(mSelf instanceof Unit) {
        Unit e = (Unit)mSelf;
        if(Point.distance(e.getPosition(), new Vector2f(x,y)) < 10.f) {
          
          // We have completed our task!
          complete();
          return;
          
        }
      }
      
    }
    
    @Override
    public Command onCompleted() {
      System.out.println("Move command finished!");
      return null;
    }
    
    @Override
    public void issue(Object self) {
        
        super.issue(self);
        
        // Only units can use this command
        if(self instanceof Unit) {
            
            Unit e = (Unit)self;
            
            // Remove existing behaviour
            e.removeBehaviour(BPathFindTo.ID);
            
            // Create a new one
            e.addBehaviour(new BPathFindTo( new Data( scene, e ), x, y ));
            
            // Ensure this unit doesn't already have a MoveTo behaviour
            //e.removeBehaviour(BMoveTo.ID);
            
            // Make the object move to x,y
            //e.addBehaviour(new BMoveTo(e, x, y));
            
        }
        
    }
    
}
