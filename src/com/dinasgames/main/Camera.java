/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main;

import com.dinasgames.main.Scenes.Scene;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Networking.Network;

/**
 *
 * @author Jack
 */
public class Camera {
    
    protected Vector2f mPosition;
    
    public static Camera getCurrent() {
        if(Network.isServer()) {
            return null;
        }
        return Scene.getCurrent().getCamera();
    }
    
    public Camera() {
        mPosition = new Vector2f(0.f,0.f);
    }
    
    public void tick() {
        
    }
    
    // Setters
    public void setPosition(Vector2f position) {
        mPosition = position;
    }
    
    public void setPosition(float x, float y) {
        mPosition.x = x;
        mPosition.y = y;
    }
    
    // Getters
    public Vector2f getPosition() {
        return mPosition;
    }
    
    // Other
    public void move(Vector2f offset) {
        mPosition.x += offset.x;
        mPosition.y += offset.y;
    }
    
    public void move(float x, float y) {
        mPosition.x += x;
        mPosition.y += y;
    }
    
}
