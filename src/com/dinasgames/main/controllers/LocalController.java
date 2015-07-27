package com.dinasgames.main.controllers;

import com.dinasgames.main.inputs.LocalInput;

/**
 * Input controller for the local player
 * @author Jack
 */
public class LocalController extends Controller {
    
    public LocalController() {
        mInput = new LocalInput();
    }
    
    @Override
    public void update() {
        
        super.update();
        
        ((LocalInput)mInput).update();
        
    }
    
}
