/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Objects;

import com.dinasgames.main.Games.Game;
import com.dinasgames.main.Scenes.Scene;
import com.dinasgames.main.Math.Vector2f;

/**
 *
 * @author Jack
 */
public class GameObject {
    
    /**
     * Whether this is a reference to an object.
     */
    protected boolean mIsReference;
    
    /**
     * The unique ID for this object.
     */
    protected int mID;
    
    /**
     * The position of the game object.
     */
    protected Vector2f mPosition;
    
    /**
     * The rotation is degrees of this game object.
     */
    protected float mRotation;
    
    protected GameObject() {
        mPosition = new Vector2f(0.f,0.f);
        mRotation = 0.f;
        mID = -1;
        mIsReference = false;
    }

    public boolean isReference() {
        return (mIsReference && hasValidReference());
    }
    
    public void makeReference() {
        mIsReference = true;
    }
    
    protected boolean hasValidReference() {
        return (ref() != null);
    }

    private GameObject ref() {
        return Scene.getCurrent().get(mID);
    }
    
    public boolean hasType(GameObjectType type) {
        if(isReference()) {
            return ref().hasType(type);
        }
        return (getTypeID() & type.getID()) > 0;
    }
    
    public int getTypeID() {
        if(isReference()) {
            return ref().getTypeID();
        }
        return GameObjectType.GameObject.getID();
    }
    
    public String getTypeString() {
        if(isReference()) {
            return ref().getTypeString();
        }
        return "GameObject";
    }
    
    // Events
    public void onCreate() {
        
    }
    
    public void onDestroy() {
        
    }
    
    public void onTick(double time) {
        
    }
    
    public void onRender() {
        
    }
    
    
    
    // Setter methods
    public void setID(int id) {
        mID = id;
    }
    
    public void setPosition(Vector2f position) {
        mPosition = position;
        if(isReference()) {
            ref().setPosition(position);
        }
    }
    
    public void setPosition(float x, float y) {
        mPosition.x = x;
        mPosition.y = y;
        if(isReference()) {
            ref().setPosition(x,y);
        }
    }
    
    public void setRotation(float rotation) {
        mRotation = rotation;
        if(isReference()) {
            ref().setRotation(rotation);
        }
    }
    
    // Getter methods
    public Vector2f getPosition() {
        if(isReference()) {
            return ref().getPosition();
        }
        return mPosition;
    }
    
    public float getRotation() {
        if(isReference()) {
            return ref().getRotation();
        }
        return mRotation;
    }
    
    public int getID() {
        return mID;
    }
    
    public void destroy() {
        if( Scene.getCurrent() != null ) {
            Scene.getCurrent().remove(mID);
        }
    }
    
}
