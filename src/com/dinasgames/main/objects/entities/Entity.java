package com.dinasgames.main.objects.entities;

import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.HealthbarShape;
import com.dinasgames.main.math.BoundingBox;
import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.objects.GameObject;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.scenes.Scene;

/**
 *
 * @author Jack
 */
public class Entity extends GameObject {
    
    protected Player mOwner;
    protected boolean mSelected;
    protected float mHealth, mHealthMax;
    protected HealthbarShape mHealthbar;
    protected Vector2f mSize;

    protected BoundingBox mBoundingBox;
    protected boolean mDead;
    
    protected Entity() {
        mHealth = mHealthMax = 0.f;
        mHealthbar = null;
        mBoundingBox = new BoundingBox();
        mDead = false;
        mSelected = false;
        mOwner = null;
        mSize = new Vector2f(0.f,0.f);
    }
    
    protected Entity(Scene scene) {
        
        super(scene);
        
        mHealth = mHealthMax = 0.f;
        mHealthbar = null;
        mBoundingBox = new BoundingBox();
        mDead = false;
        mSelected = false;
        mOwner = null;
        mSize = new Vector2f(0.f,0.f);
        
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Entity.getID();
    }
    
    @Override
    public String getTypeString() {
        return "Entity";
    }
    
    // Events
    public void onDeath() {
        mDead = true;
    }
    
    @Override
    public void onCreate() {
        
        mHealthbar = new HealthbarShape();
        
        mHealthbar.setHeight(5.f);
        mHealthbar.setFillColor(Color.BLACK);
        mHealthbar.setForegroundColor(Color.RED);
        mHealthbar.setOutlineThickness(1.f);
        mHealthbar.setOutlineColor(Color.BLACK);
        mHealthbar.setHealth(mHealth);
        mHealthbar.setMaxHealth(mHealthMax);
        mHealthbar.setScene(mScene);
        
        
        // Make sure the healthbar renders in front of other things
        mHealthbar.setDepth(-100);
        mHealthbar.render(mRenderer);
        
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
        
        // Remove the healthbar
        mHealthbar.remove();
        
    }
    
    @Override
    public void onTick(double time) {
        
        // Update bounding box
        recalculateBoundingBox();

    }
    
    @Override
    public void onRender() {
        
        // Update healthbar
        if(mSelected) {
            
            // Update the healthbar position
            mHealthbar.setPosition(mBoundingBox.x, mBoundingBox.y-10);
            
            // Update the healthbar health
            mHealthbar.setHealth(mHealth);
            
            // Update the healthbar size
            mHealthbar.setSize(mBoundingBox.width, mHealthbar.getSize().y);
            
            // Make sure the healthbar is visible
            if(!mHealthbar.isVisible()) {
                mHealthbar.show();
            }
            
        }else{
            
            // Hide the healthbar when this entity isn't selected
            if(mHealthbar.isVisible()) {
                mHealthbar.hide();
            }
            
        }

    }
    
    protected void recalculateBoundingBox() {
        mBoundingBox.setPosition(mPosition.x-mSize.x/2, mPosition.y-mSize.y/2);
    }
    
    public void setOwner(Player owner) {
        mOwner = owner;
        if(mOwner == null) {
            return;
        }
        onNewOwner();
    }
    
    public Player getOwner() {
        return mOwner;
    }
    
    public void setSize(Vector2f size) {
        mSize = size;
    }
    
    public void setSize(float width, float height) {
        mSize.x = width;
        mSize.y = height;
    }
    
    public float getWidth() {
        return mSize.x;
    }
    
    public float getHeight() {
        return mSize.y;
    }
    
    public Vector2f getSize() {
        return mSize;
    }
    
    public void select() {
        mSelected = true;
    }
    
    public void deselect() {
        mSelected = false;
    }
    
    public boolean isSelected() {
        return mSelected;
    }
    
    public void setSelected(boolean selected) {
        mSelected = selected;
    }
    
    // Getters
    public BoundingBox getBoundingBox() {
        return mBoundingBox;
    }
    
    public boolean isDead() {
        return mDead;
    }
    
    public float getHealth() {
        return mHealth;
    }
    
    public float getMaxHealth() {
        return mHealthMax;
    }
    
    // Setters
    public void setHealth(float hp) {
        mHealth = Math.max(0.f, Math.min(mHealthMax, hp));
    }
    
    public void setMaxHealth(float hp) {
        mHealthMax = hp;
    }
    
    // Methods
    public void damage(float amount) {
        if(mDead) {
            return;
        }
        mHealth = Math.max(0.f, Math.min(mHealthMax, mHealth - amount));
        if(mHealth <= 0.f) {
            onDeath();
        }
    }
    
    public void kill() {
        if(mDead) {
            return;
        }
        mHealth = 0.f;
        onDeath();
    }
    
}