/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Inputs;

import com.dinasgames.main.System.Keyboard;
import com.dinasgames.main.System.Mouse;

/**
 *
 * @author Jack
 */
public class LocalInput extends Input {
    
    public LocalInput() {
        
    }
    
    public LocalInput(LocalInput other) {
        super((Input)other);
    }
    
    public void update() {
        
        mousePressed    = Mouse.isButtonPressed(Mouse.Button.Left);
        left            = Keyboard.isKeyPressed(Keyboard.Key.Left);
        right           = Keyboard.isKeyPressed(Keyboard.Key.Right);
        down            = Keyboard.isKeyPressed(Keyboard.Key.Down);
        up              = Keyboard.isKeyPressed(Keyboard.Key.Up);
        control         = Keyboard.isKeyPressed(Keyboard.Key.Control);
        shift           = Keyboard.isKeyPressed(Keyboard.Key.Shift);
        mousePosition   = Mouse.getPosition();
        
    }
    
}
