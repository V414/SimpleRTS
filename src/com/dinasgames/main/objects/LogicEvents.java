/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects;

import com.dinasgames.engine.system.Time;

/**
 *
 * @author Jack
 */
public interface LogicEvents {
    
    void onTick( Time timePassed ); // << Called every game 'step'
    
}
