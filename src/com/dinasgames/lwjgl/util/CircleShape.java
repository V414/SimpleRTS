/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.math.Vector2f;

/**
 *
 * @author Jack
 */
public class CircleShape extends Shape {
    
    float mRadius;
    int mPointCount;
    
    public CircleShape(float radius, int pointCount) {
        mRadius = radius;
        mPointCount = pointCount;
        update();
    }
    
    public CircleShape(float size) {
        this(size, 10);
    }
    
    public CircleShape setRadius(float radius) {
        mRadius = radius;
        update();
        return this;
    }
    
    public float getRadius() {
        return mRadius;
    }
    
    @Override
    public CircleShape setOriginCenter() {
        setOrigin( mRadius, mRadius );
        return this;
    }
    
    public CircleShape setPointCount(int count) {
        mPointCount = count;
        update();
        return this;
    }
    
    @Override
    public int getPointCount() {
        return mPointCount;
    }
    
    @Override
    public Vector2f getPoint(int idx) {
        
        float angle = (float)idx * 2.f * (float)Math.PI / (float)mPointCount - (float)Math.PI / 2.f;
        float x = (float)Math.cos(angle) * mRadius;
        float y = (float)Math.sin(angle) * mRadius;
        
        return new Vector2f(mRadius + x, mRadius + y);
    }
    
}
