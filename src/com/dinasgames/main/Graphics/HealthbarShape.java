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
        ghost.setID(id);
        
        return ghost;
        
    }
    
    private HealthbarShape self() {
        return (HealthbarShape)Renderer.getCurrent().get(mID);
    }
    
    public HealthbarShape setHealth(float hp) {
        if(mHealth != hp) {
            mHealth = hp;
            recalculate();
        }
        if(inRenderQueue()) {
            self().setHealth(hp);
        }
        return this;
    }
    
    public HealthbarShape setMaxHealth(float max) {
        if(mHealthMax != max) {
            mHealthMax = max;
            recalculate();
        }
        if(inRenderQueue()) {
            self().setMaxHealth(max);
        }
        return this;
    }
    
    public HealthbarShape setForegroundColor(Color color) {
        mForegroundColor = color;
        if(inRenderQueue()) {
            self().setForegroundColor(color);
        }
        return this;
    }
    
    public Color getForegroundColor() {
        if(inRenderQueue()) {
            return self().getForegroundColor();
        }
        return mForegroundColor;
    }
    
    public float getHealth() {
        if(inRenderQueue()) {
            return self().getHealth();
        }
        return mHealth;
    }
    
    public float getMaxHealth() {
        if(inRenderQueue()) {
            return self().getMaxHealth();
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
