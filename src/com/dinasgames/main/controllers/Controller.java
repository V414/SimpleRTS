/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.controllers;

import com.dinasgames.main.inputs.Input;

/**
 *
 * @author Jack
 */
public class Controller {
    
    protected Input mInput, mPreviousInput;
    
    protected Controller() {
        mInput = mPreviousInput = null;
    }
    
    public void update() {
        
    }
    
    public Input getInput() {
        return mInput;
    }
    
    public Input getPreviousInput() {
      return mPreviousInput;
    }
    
}
