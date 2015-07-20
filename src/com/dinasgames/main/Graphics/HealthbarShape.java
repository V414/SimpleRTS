/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Graphics;

import com.dinasgames.main.Camera;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import com.dinasgames.main.Math.Vector2f;

/**
 *
 * @author Jack
 */
public class HealthbarShape extends Shape {
    
    protected Color mForegroundColor;
    protected Rectangle mBackground, mForeground;
    protected float mHealth, mHealthMax;
    
    protected HealthbarShape() {
        mForegroundColor = Color.red;
        mSize = new Vector2f(100.f, 10.f);
        mHealth = 100.f;
        mHealthMax = 100.f;
    }
    
    public static HealthbarShape create() {
        
        // Add a rectangle shape to the render queue
        int id = Renderer.getCurrent().add(new HealthbarShape());
        
        // Create a ghost
        HealthbarShape ghost = new HealthbarShape();
        ghost.makeReference();
        ghost.setID(id);
        
        return ghost;
        
    }
    
    @Override
    protected boolean hasValidReference() {
        return (ref() != null);
    }

    private HealthbarShape ref() {
        return (HealthbarShape)Renderer.getCurrent().get(mID);
    }
    
    public HealthbarShape setHealth(float hp) {
        if(isReference()) {
            ref().setHealth(hp);
            return this;
        }
        if(mHealth != hp) {
            mHealth = hp;
            recalculate();
        }
        return this;
    }
    
    public HealthbarShape setMaxHealth(float max) {
        if(isReference()) {
            ref().setMaxHealth(max);
            return this;
        }
        if(mHealthMax != max) {
            mHealthMax = max;
            recalculate();
        }
        return this;
    }
    
    public HealthbarShape setForegroundColor(Color color) {
        if(isReference()) {
            ref().setForegroundColor(color);
            return this;
        }
        mForegroundColor = color;
        return this;
    }
    
    public Color getForegroundColor() {
        if(isReference()) {
            return ref().getForegroundColor();
        }
        return mForegroundColor;
    }
    
    public float getHealth() {
        if(isReference()) {
            return ref().getHealth();
        }
        return mHealth;
    }
    
    public float getMaxHealth() {
        if(isReference()) {
            return ref().getMaxHealth();
        }
        return mHealthMax;
    }
    
    @Override
    public void recalculate() {
        
        // Calculate foreground size
        Vector2f foregroundSize = new Vector2f();
        
        if(mHealth <= 0.f || mHealthMax <= 0.f) {
            // Don't divide by zero!
            foregroundSize.x = 0;
        }else{
            foregroundSize.x = Math.max(0.f, Math.min(mSize.x, (mHealth / mHealthMax) * mSize.x));
        }
        foregroundSize.y = mSize.y;
        
        // Create background
        mBackground = new Rectangle(0, 0, (int)mSize.x, (int)mSize.y);
        
        // Create foreground
        if(foregroundSize.x > 0.f) {
            mForeground = new Rectangle(0,0, (int)foregroundSize.x, (int)foregroundSize.y);
        }else{
            mForeground = null;
        }
        
    }
    
    @Override
    public void render(Graphics2D g) {
        
        if(mSize.x == 0.f && mSize.y == 0.f) {
            return;
        }
        
        AffineTransform oldTransform = g.getTransform();
        
        // Apply camera transformation
        g.translate(-Camera.getCurrent().getPosition().x, -Camera.getCurrent().getPosition().y);
        
        // Apply our position
        g.translate(mPosition.x, mPosition.y);
        
        // Apply our rotation
        g.rotate(Math.toRadians(mRotation), mOrigin.x, mOrigin.y);
        
        // Apply our scale
        g.scale(mScale.x, mScale.y);
        
        // Draw background
        if(mFillColor.getAlpha() > 0 && mBackground != null) {
            g.setColor(mFillColor);
            g.fill(mBackground);
        }
        
        // Draw Foreground
        if(mForegroundColor.getAlpha() > 0 && mForeground != null) {
            g.setColor(mForegroundColor);
            g.fill(mForeground);
        }
        
        // Draw outline
        if(mOutlineThickness > 0.f && mOutlineColor.getAlpha() > 0) {
            Stroke oldStroke = g.getStroke();
            g.setStroke(new BasicStroke(mOutlineThickness));
            g.setColor(mOutlineColor);
            g.draw(mBackground);
            g.setStroke(oldStroke);
        }    
        
        // Reset transform
        g.setTransform(oldTransform);
        
    }
    
}
