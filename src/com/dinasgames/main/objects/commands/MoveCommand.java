/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.commands;

import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.objects.behaviours.BMoveTo;
import com.dinasgames.main.objects.behaviours.BMoveToWithAcceleration;
import com.dinasgames.main.objects.entities.Entity;

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
