/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.inputs;

import com.dinasgames.engine.math.Vector2f;

/**
 *
 * @author Jack
 */
public class Input {
    
    public boolean mousePressed, left, right, up, down, control, shift, mousePressedR, keyP, rightShift;
    public Vector2f mousePosition;
    
    public Input() {
        mousePressed = left = right = up = down = mousePressedR = keyP = rightShift = false;
        mousePosition = new Vector2f(0.f,0.f);
    }
    
    public Input(Input other) {
        mousePressed = other.mousePressed;
        left = other.left;
        right = other.right;
        up = other.up;
        down = other.down;
        control = other.control;
        shift = other.shift;
        mousePressedR = other.mousePressedR;
        mousePosition = new Vector2f(other.mousePosition);
        keyP = other.keyP;
        rightShift = other.rightShift;
    }
    
}
