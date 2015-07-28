/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.behaviours;

import com.dinasgames.main.system.Time;

/**
 *
 * @author Jack
 */
public class Behaviour {
    
    protected Object self;
    
    public Behaviour(Object self) {
        this.self = self;
    }
    
    public Object getSelf() {
        return self;
    }
    
    public void update(Time timePassed) {
        
    }
    
    public int getUniqueID() {
        return 0;
    }
    
    public boolean equals(Behaviour other) {
        return (other.getUniqueID() == getUniqueID());
    }
    
}
