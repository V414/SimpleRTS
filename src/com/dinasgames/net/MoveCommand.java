/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.net;

import com.dinasgames.main.objects.GameObject;
import com.dinasgames.main.objects.entities.units.Unit;
import com.dinasgames.main.system.Time;

/**
 *
 * @author Jack
 */
public class MoveCommand extends Command {

    public float targetX, targetY;
    
    public MoveCommand(Object self, float x, float y) {
        super(self);
        this.targetX = x;
        this.targetY = y;
    }
    
    @Override
    public void update(Time timePassed) {
        
        // Make sure we have a valid object
        if(self == null) {
            return;
        }
        
        if(self instanceof Unit) {
            Unit unit = (Unit)self;
            if(unit != null) {
                
                //unit.getSpeed();
                
            }
        }
        
    }
    
}
