/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.inputs;

import com.dinasgames.engine.system.Keyboard;
import com.dinasgames.engine.system.Mouse;

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
        mousePressedR   = Mouse.isButtonPressed(Mouse.Button.Right);
        left            = Keyboard.isKeyPressed(Keyboard.Key.Left);
        right           = Keyboard.isKeyPressed(Keyboard.Key.Right);
        down            = Keyboard.isKeyPressed(Keyboard.Key.Down);
        up              = Keyboard.isKeyPressed(Keyboard.Key.Up);
        control         = Keyboard.isKeyPressed(Keyboard.Key.LeftControl);
        shift           = Keyboard.isKeyPressed(Keyboard.Key.LeftShift);
        rightShift      = Keyboard.isKeyPressed(Keyboard.Key.RightShift);
        mousePosition   = Mouse.getPosition();
        keyP            = Keyboard.isKeyPressed(Keyboard.Key.P);
        keyB            = Keyboard.isKeyPressed(Keyboard.Key.B);
        keyW            = Keyboard.isKeyPressed(Keyboard.Key.W);
    }
    
}
