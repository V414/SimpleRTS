/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Players;

import com.dinasgames.main.Controllers.Controller;

/**
 *
 * @author Jack
 */
public class Player {
    
    protected Controller mController;
    
    protected Player() {
        mController = null;
    }
    
    public void update() {
        
        // Update controller
        if(mController != null) {
            mController.update();
        }
        
    }
    
    public Controller getController() {
        return mController;
    }
    
}
