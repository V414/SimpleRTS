/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.Math.Vector2f;
import java.awt.Color;

/**
 *
 * @author Jack
 */
public class RectangleShape extends Shape {
    
    float mWidth, mHeight;
    
    public RectangleShape(float width, float height) {
        setSize(width, height);
    }
    
    public RectangleShape(Vector2f size) {
        this(size.x, size.y);
    }
    
    @Override
    public RectangleShape setOriginCenter() {
        setOrigin( mWidth / 2.f, mHeight / 2.f );
        return this;
    }
    
    public RectangleShape setSize(float width, float height) {
        mWidth = width;
        mHeight = height;
        update();
        return this;
    }
    
    public RectangleShape setSize(Vector2f size) {
        return setSize(size.x,size.y);
    }
    
    public Vector2f getSize() {
        return new Vector2f(getWidth(), getHeight());
    }
    
    public float getWidth() {
        return mWidth;
    }
    
    public float getHeight() {
        return mHeight;
    }
    
    @Override
    public int getPointCount() {
        return 4;
    }
    
    @Override
    public Vector2f getPoint(int idx) {
        switch(idx) {
            case 0: return new Vector2f(0, 0);
            case 1: return new Vector2f(mWidth, 0);
            case 2: return new Vector2f(mWidth, mHeight);
            case 3: return new Vector2f(0, mHeight);
        }
        return null;
    }

    public void setOutlineColor(Color BLACK) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
