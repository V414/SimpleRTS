/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Objects.Misc;

import com.dinasgames.main.Camera;
import com.dinasgames.main.System.Keyboard;
import com.dinasgames.main.System.Mouse;
import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Objects.Entities.Entity;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;
import java.awt.Color;

/**
 *
 * @author Jack
 */
public class TestObject extends Entity {
    
    protected RectangleShape mShape;
    
    public TestObject() {
        
        mBoundingBox.setSize(20.f, 40.f);
        
        mHealthMax = 100.f;
        mHealth = mHealthMax;

        mShape = null;
        
    }
    
    @Override
    protected void recalculateBoundingBox() {
        
        super.recalculateBoundingBox();
        
        mBoundingBox.setSize(20.f, 40.f);
        
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.TestObject.getID();
    }
    
    @Override
    public String getTypeString() {
        return "TestObject";
    }
    
    // Events
    @Override
    public void onCreate() {
        
        super.onCreate();
        
        mShape = new RectangleShape();
        
        mShape.setFillColor(Color.yellow);
        mShape.setOutlineColor(Color.red);
        mShape.setOutlineThickness(5.f);
        mShape.setSize(20.f, 40.f);
        mShape.setOrigin(10.f,20.f);
        mShape.setRenderer(mRenderer);
        mShape.setScene(mScene);
        
    }
    
    @Override
    public void onDestroy() {
        
        super.onDestroy();
        
        mShape.remove();
        
    }
    
    @Override
    public void onTick(double time) {
        
        super.onTick(time);
        
        mPosition = Mouse.getPosition();
        mRotation ++;
        
        //Camera.getCurrent().move(-1,-1);
        
        if(Keyboard.isKeyPressed(Keyboard.Key.B)) {
            System.out.println("B pressed! " + mPosition.x);
            damage(1.f);
        }
        
    }
    
    @Override
    public void onRender() {
        
        super.onRender();
        
        // Update shape coordinates
        mShape.setPosition(mPosition);
        mShape.setRotation(mRotation);
        
    }
    
}
