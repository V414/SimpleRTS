/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.net;

import com.dinasgames.main.system.Time;

/**
 *
 * @author Jack
 */
public class Command {
    
    protected Object self;
    
    public Command(Object self) {
        this.self = self;
    }
    
    public void update(Time timePassed) {
        
    }
    
}
