/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.commands;

import com.dinasgames.engine.math.Point;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.main.behaviours.BMoveTo;
import com.dinasgames.main.behaviours.BMoveToWithAcceleration;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.engine.system.Time;

/**
 *
 * @author Jack
 */
public class MoveCommand extends Command {
    
    protected float x, y;
    
    public MoveCommand( float x, float y ) {
        this.x = x;
        this.y = y;
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
      if(mSelf instanceof Entity) {
        Entity e = (Entity)mSelf;
        if(Point.distance(e.getPosition(), new Vector2f(x,y)) < 20.f) {
          
          // We have completed our task!
          complete();
          
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
        
        if(self instanceof Entity) {
            
            Entity e = (Entity)self;
            
            // Ensure this unit doesn't already have a MoveTo behaviour
            e.removeBehaviour(BMoveTo.ID);
            
            // Make the object move to x,y
            e.addBehaviour(new BMoveTo(e, x, y));
            
        }
        
    }
    
}
