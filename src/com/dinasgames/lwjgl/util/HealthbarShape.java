/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.Math.Vector2f;

/**
 *
 * @author Jack
 */
public class HealthbarShape extends Shape {
    
    protected Color mForegroundColor;
    protected RectangleShape mBackground, mForeground;
    protected float mHealth, mHealthMax, mWidth, mHeight;
    
    public HealthbarShape() {
        mForegroundColor = Color.RED;
        mWidth = 100.f;
        mHeight = 10.f;
        mHealth = 100.f;
        mHealthMax = 100.f;
        mBackground = new RectangleShape(mWidth, mHeight);
        mForeground = new RectangleShape(mWidth, mHeight);
    }
    
    public HealthbarShape setHealth(float hp) {
        if(mHealth != hp) {
            mHealth = hp;
            updateSize();
        }
        return this;
    }
    
    public HealthbarShape setMaxHealth(float max) {
        if(mHealthMax != max) {
            mHealthMax = max;
            updateSize();
        }
        return this;
    }
    
    @Override
    public HealthbarShape setFillColor(Color color) {
        super.setFillColor(color);
        updateColors();
        return this;
    }
    
    @Override
    public HealthbarShape setOutlineColor(Color color) {
        super.setOutlineColor(color);
        updateColors();
        return this;
    }
    
    @Override
    public HealthbarShape setOutlineThickness(float val) {
        super.setOutlineThickness(val);
        updateColors();
        return this;
    }
    
    public HealthbarShape setForegroundColor(Color color) {
        mForegroundColor = color;
        updateColors();
        return this;
    }
    
    public Color getForegroundColor() {
        return mForegroundColor;
    }
    
    public float getHealth() {
        return mHealth;
    }
    
    public float getMaxHealth() {
        return mHealthMax;
    }
    
    @Override
    public void onAdd() {
        mBackground.render(mRenderer);
        mForeground.render(mRenderer);
    }
    
    @Override
    public void onRemove() {
        
        if(mRenderer == null) {
            return;
        }
        
        mRenderer.remove(mBackground.getID());
        mRenderer.remove(mForeground.getID());
        
    }
    
    protected void updateColors() {
        
        mBackground.setFillColor(mFillColor);
        mBackground.setOutlineColor(mOutlineColor);
        mBackground.setOutlineThickness(mOutlineThickness);
        
        mForeground.setFillColor(mForegroundColor);
        mForeground.setOutlineColor(mOutlineColor);
        mForeground.setOutlineThickness(mOutlineThickness);
        
    }
    
    protected void updateSize() {
        
        // Calculate foreground size
        Vector2f foregroundSize = new Vector2f();
        
        if(mHealth <= 0.f || mHealthMax <= 0.f) {
            // Don't divide by zero!
            foregroundSize.x = 0;
        }else{
            foregroundSize.x = Math.max(0.f, Math.min(mWidth, (mHealth / mHealthMax) * mWidth));
        }
        foregroundSize.y = mHeight;
        
        // Create background
        mBackground.setSize(mWidth, mHeight);
        
        // Create foreground
        if(foregroundSize.x > 0.f) {
            mForeground.show();
            mForeground.setSize(foregroundSize);
        }else{
            mForeground.hide();
        }
        
    }
    
    protected void updatePosition() {
        
        // Position
        mBackground.setPosition(mPosition);
        mForeground.setPosition(mPosition);
        
        // Scale
        mBackground.setScale(mScale);
        mForeground.setScale(mScale);
        
        // Origin
        mBackground.setOrigin(mOrigin);
        mForeground.setOrigin(mOrigin);
        
        // Rotation
        mBackground.setRotation(mRotation);
        mForeground.setRotation(mRotation);
        
        mBackground.setVisible(mVisible);
        mForeground.setVisible(mVisible);
        
    }
    
    protected void updateAll() {
        
        updatePosition();
        updateSize();
        updateColors();
        
    }

    public HealthbarShape setWidth(float width) {
        mWidth = width;
        return this;
    }
    
    public HealthbarShape setHeight(float height) {
        mHeight = height;
        return this;
    }
    
    public HealthbarShape setSize(float width, float height) {
        mWidth = width;
        mHeight = height;
        return this;
    }
    
    public HealthbarShape setSize(Vector2f size) {
        return setSize(size.x,size.y);
    }
    
    public float getWidth() {
        return mWidth;
    }
    
    public float getHeight() {
        return mHeight;
    }
    
    public Vector2f getSize() {
        return new Vector2f(mWidth, mHeight);
    }
    
    @Override
    public void draw(RenderTarget target, RenderStates states) {
        
        // Update shapes
        updateAll();
        
        // Draw background
        mBackground.draw(target, states);
        
        // Draw foreground
        if(mForeground.isVisible()) {
            mForeground.draw(target, states);
        }
        
    }
    
}
