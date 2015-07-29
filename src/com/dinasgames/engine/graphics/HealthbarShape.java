/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.graphics;

import com.dinasgames.engine.graphics.shapes.Shape;
import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.engine.math.Vector2f;

/**
 *
 * @author Jack
 */
public class HealthbarShape extends Shape {
    
    protected Color mForegroundColor;
    protected RectangleShape mBackground, mForeground;
    protected float mHealth, mHealthMax, mWidth, mHeight;
    
    public HealthbarShape() {
        
        mForegroundColor = Color.WHITE;
        
        mWidth = 100.f;
        mHeight = 10.f;
        mHealth = 100.f;
        mHealthMax = 100.f;
        mBackground = new RectangleShape(mWidth, mHeight);
        mForeground = new RectangleShape(mWidth, mHeight);
        mBackground.hide();
        mForeground.hide();
        
    }
    
    public void onHeightChange( float oldValue, float newValue ) {
        updateSize( mWidth, newValue );
    }
    
    public void onWidthChange( float oldValue, float newValue ) {
        updateSize( newValue, mHeight );
    }
    
    public void onMaxHealthChange( float oldValue, float newValue ) {
        
    }
    
    public void onHealthChange( float oldValue, float newValue ) {
        
    }
    
    public void onForegroundColorChange( Color oldValue, Color newValue ) {
        updateForegroundColor(newValue);
    }
    
    @Override
    public void onScaleChange( Vector2f oldValue, Vector2f newValue ) {
        updateScale(newValue);
    }
    
    @Override
    public void onOriginChange( Vector2f oldValue, Vector2f newValue ) {
        updateOrigin( newValue );
    }
    
    @Override
    public void onPositionChange( Vector2f oldValue, Vector2f newValue ) {
        updatePosition(newValue);
    }
    
    @Override
    public void onFillColorChange( Color oldValue, Color newValue ) {
        updateFillColor( newValue );
    }
    
    @Override
    public void onOutlineColorChange( Color oldValue, Color newValue ) {
        updateOutlineColor( newValue );
    }
    
    @Override
    public void onOutlineThicknessChange( float oldValue, float newValue ) {
        updateOutlineThickness(newValue);
    }
    
    @Override
    public void onVisibilityChange(boolean oldValue, boolean newValue) {
        updateVisibility(newValue);
    }
    
    public HealthbarShape setHealth(float hp) {
        
        if(mHealth == hp) {
            return this;
        }
        
        onHealthChange( mHealth, hp );
        mHealth = hp;
        
        updateForeground(mWidth, mHeight);
        
        return this;
    }
    
    public HealthbarShape setMaxHealth(float max) {
        
        if(mHealthMax == max) {
            return this;
        }
        
        onMaxHealthChange( mHealthMax, max );
        mHealthMax = max;
        
        updateForeground(mWidth, mHeight);
        
        return this;
        
    }
    
    public HealthbarShape setForegroundColor(Color color) {
        
        if(mForegroundColor.equals(color)) {
            return this;
        }
        
        onForegroundColorChange( mForegroundColor, color );
        mForegroundColor = color;
        
        return this;
    }
    
    public Color getForegroundColor() {
        return new Color(mForegroundColor);
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
    
    protected void updateFillColor(Color fillColor) {
        mBackground.setFillColor(fillColor);
    }
    
    protected void updateOutlineColor(Color outlineColor) {
        mBackground.setOutlineColor(outlineColor);
        mForeground.setOutlineColor(outlineColor);
    }
    
    protected void updateOutlineThickness(float val) {
        mBackground.setOutlineThickness(val);
        mForeground.setOutlineThickness(val);
    }
    
    protected void updateForegroundColor(Color foreColor) {
        mForeground.setFillColor(foreColor);
    }
    
    protected void updatePosition(Vector2f pos) {
        mBackground.setPosition(pos);
        mForeground.setPosition(pos);
    }
    
    protected void updateScale(Vector2f scale) {
        mBackground.setScale(scale);
        mForeground.setScale(scale);
    }
    
    protected void updateOrigin(Vector2f origin) {
        mBackground.setOrigin(origin);
        mForeground.setOrigin(origin);
    }
    
    protected void updateForeground(float width, float height) {
        
        // Calculate foreground size
        Vector2f foregroundSize = new Vector2f();
        
        if(mHealth <= 0.f || mHealthMax <= 0.f) {
            // Don't divide by zero!
            foregroundSize.x = 0;
        }else{
            foregroundSize.x = Math.max(0.f, Math.min(width, (mHealthMax / mHealth) * width));
        }
        foregroundSize.y = height;
        
        // Create foreground
        if(foregroundSize.x > 0.f) {
            mForeground.show();
            mForeground.setSize(foregroundSize);
        }else{
            mForeground.hide();
        }
        
    }
    
    protected void updateVisibility(boolean vis) {
        
        mBackground.setVisible(vis);
        mForeground.setVisible(vis);
        
        if(vis) {
            updateForeground(mWidth, mHeight);
        }
        
    }
    
    protected void updateSize( float width, float height ) {
        
        // Create background
        mBackground.setSize(width, height);
        updateForeground( width, height );
        
    }

    public HealthbarShape setWidth(float width) {
        
        if(mWidth == width) {
            return this;
        }
        
        onWidthChange( mWidth, width );
        mWidth = width;
        
        return this;
        
    }
    
    public HealthbarShape setHeight(float height) {
        
        if(mHeight == height) {
            return this;
        }
        
        onHeightChange( mHeight, height );
        mHeight = height;
        
        return this;
    }
    
    public HealthbarShape setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
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
        
        // Draw background
        mBackground.draw(target, states);
        
        // Draw foreground
        if(mForeground.isVisible()) {
            mForeground.draw(target, states);
        }
        
    }
    
}
