/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Controllers;

import com.dinasgames.main.Inputs.Input;

/**
 *
 * @author Jack
 */
public class Controller {
    
    protected Input mInput;
    
    protected Controller() {
        mInput = null;
    }
    
    public void update() {
        
    }
    
    public Input getInput() {
        return mInput;
    }
    
}
