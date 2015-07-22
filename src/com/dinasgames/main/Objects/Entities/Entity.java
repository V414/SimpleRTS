package com.dinasgames.main.Objects.Entities;

import com.dinasgames.main.Graphics.HealthbarShape;
import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Math.BoundingBox;
import com.dinasgames.main.Objects.GameObject;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Players.Player;
import com.dinasgames.main.Scenes.Scene;
import java.awt.Color;

/**
 *
 * @author Jack
 */
public class Entity extends GameObject {
    
    protected Player mOwner;
    protected boolean mSelected;
    protected float mHealth, mHealthMax;
    protected HealthbarShape mHealthbar;
    //protected RectangleShape mSelectionBox;
    protected BoundingBox mBoundingBox;
    protected boolean mDead;
    
    protected Entity() {
        mHealth = mHealthMax = 0.f;
        mHealthbar = null;
        mBoundingBox = new BoundingBox();
        mDead = false;
        mSelected = false;
        mOwner = null;
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
        mHealthbar.setHealth(mHealth);
        mHealthbar.setMaxHealth(mHealthMax);
        
        // Make sure the healthbar renders in front of other things
        mHealthbar.setDepth(-100);
        
        //mSelectionBox = RectangleShape.create();
        //mSelectionBox.setFillColor(new Color(0,0,0,0));
        //mSelectionBox.setOutlineColor(Color.white);
        //mSelectionBox.setOutlineThickness(1.f);
        //mSelectionBox.setDepth(-101);
        
    }
    
    public void onNewOwner() {
        
    }
    
    @Override
    public void onDestroy() {
        mHealthbar.remove();
    }
    
    @Override
    public void onTick(double time) {
        
        // Update bounding box
        recalculateBoundingBox();
        
        //mSelectionBox.setPosition(mBoundingBox.left, mBoundingBox.top);
        //mSelectionBox.setSize(mBoundingBox.width, mBoundingBox.height);
    }
    
    @Override
    public void onRender() {
        
        // Update healthbar
        if(mSelected) {
            mHealthbar.setPosition(mBoundingBox.left, mBoundingBox.top-10);
            mHealthbar.setHealth(mHealth);
            mHealthbar.setSize(mBoundingBox.width, mHealthbar.getSize().y);
            mHealthbar.show();
        }else{
            mHealthbar.hide();
        }

    }
    
    protected void recalculateBoundingBox() {
        mBoundingBox.setPosition(mPosition);
    }
    
    public void setOwner(Player owner) {
        if(isReference()) {
            ref().setOwner(owner);
            return;
        }
        mOwner = owner;
        if(mOwner == null) {
            return;
        }
        onNewOwner();
    }
    
    public Player getOwner() {
        if(isReference()) {
            return ref().getOwner();
        }
        return mOwner;
    }
    
    public void select() {
        if(isReference()) {
            ref().select();
            return;
        }
        mSelected = true;
    }
    
    public void deselect() {
        if(isReference()) {
            ref().deselect();
            return;
        }
        mSelected = false;
    }
    
    public boolean isSelected() {
        if(isReference()) {
            return ref().isSelected();
        }
        return mSelected;
    }
    
    public void setSelected(boolean selected) {
        if(isReference()) {
            ref().setSelected(selected);
            return;
        }
        mSelected = selected;
    }
    
    // Getters
    public BoundingBox getBoundingBox() {
        if(isReference()) {
            return ref().getBoundingBox();
        }
        return mBoundingBox;
    }
    
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
