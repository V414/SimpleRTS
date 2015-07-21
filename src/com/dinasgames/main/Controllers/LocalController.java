package com.dinasgames.main.Controllers;

import com.dinasgames.main.Inputs.LocalInput;

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
