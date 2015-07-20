/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Objects.Entities;

import com.dinasgames.main.Graphics.HealthbarShape;
import com.dinasgames.main.Math.BoundingBox;
import com.dinasgames.main.Objects.GameObject;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;
import java.awt.Color;

/**
 *
 * @author Jack
 */
public class Entity extends GameObject {
    
    protected float mHealth, mHealthMax;
    protected HealthbarShape mHealthbar;
    protected BoundingBox mBoundingBox;
    protected boolean mDead;
    
    protected Entity() {
        mHealth = mHealthMax = 0.f;
        mHealthbar = null;
        mBoundingBox = new BoundingBox();
        mDead = false;
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Entity.getID();
    }
    
    @Override
    public String getTypeString() {
        return "Entity";
    }
    
    @Override
    protected boolean hasValidReference() {
        return (this.ref() != null);
    }

    private Entity ref() {
        return (Entity)Scene.getCurrent().get(mID);
    }
    
    // Events
    public void onDeath() {
        mDead = true;
    }
    
    @Override
    public void onCreate() {
        
        mHealthbar = HealthbarShape.create();
        
        mHealthbar.setHeight(5.f);
        mHealthbar.setFillColor(Color.black);
        mHealthbar.setForegroundColor(Color.red);
        mHealthbar.setOutlineThickness(1.f);
        mHealthbar.setOutlineColor(Color.black);
        mHealthbar.setMaxHealth(mHealth);
        mHealthbar.setMaxHealth(mHealthMax);
        
        // Make sure the healthbar renders in front of other things
        mHealthbar.setDepth(-100);
        
    }
    
    @Override
    public void onDestroy() {
        mHealthbar.remove();
    }
    
    @Override
    public void onTick(double time) {
        
        // Update bounding box
        //mBoundingBox.setPosition(mPosition);
        recalculateBoundingBox();
    }
    
    @Override
    public void onRender() {
        
        // Update healthbar
        mHealthbar.setPosition(mBoundingBox.left-10, mBoundingBox.top-10);
        mHealthbar.setWidth(mBoundingBox.width+20);
        mHealthbar.setHealth(mHealth);
        
        
    }
    
    protected void recalculateBoundingBox() {
        mBoundingBox.setPosition(mPosition);
    }
    
    // Getters
    public boolean isDead() {
        if(isReference()) {
            return ref().isDead();
        }
        return mDead;
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
    
    // Setters
    public void setHealth(float hp) {
        mHealth = Math.max(0.f, Math.min(mHealthMax, hp));
        if(isReference()) {
            ref().setHealth(hp);
        }
    }
    
    public void setMaxHealth(float hp) {
        mHealthMax = hp;
        if(isReference()) {
            ref().setMaxHealth(hp);
        }
    }
    
    // Methods
    public void damage(float amount) {
        if(isReference()) {
            ref().damage(amount);
        }
        if(mDead) {
            return;
        }
        mHealth = Math.max(0.f, Math.min(mHealthMax, mHealth - amount));
        if(mHealth <= 0.f) {
            onDeath();
        }
    }
    
    public void kill() {
        if(isReference()) {
            ref().kill();
        }
        if(mDead) {
            return;
        }
        mHealth = 0.f;
        onDeath();
    }
    
}
