/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Controllers;

import com.dinasgames.main.Inputs.Input;
import com.dinasgames.main.Inputs.LocalInput;

/**
 *
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
