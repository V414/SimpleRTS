/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.graphics;

import com.dinasgames.engine.math.Vector2f;

/**
 *
 * @author Jack
 */
public class Transformable {
    
    protected float mOriginX, mOriginY, mPositionX, mPositionY, mScaleX, mScaleY, mRotation;
    protected Transform mTransform, mInverseTransform;
    protected boolean mTransformNeedUpdate, mInverseTransformNeedUpdate;
    
    public Transformable() {
        
        this.mOriginX   = 0.f;
        this.mOriginY   = 0.f;
        this.mPositionX = 0.f;
        this.mPositionY = 0.f;
        this.mScaleX    = 1.f;
        this.mScaleY    = 1.f;
        this.mRotation  = 0.f;

        this.mTransform                     = new Transform();
        this.mInverseTransform              = new Transform();
        this.mTransformNeedUpdate           = true;
        this.mInverseTransformNeedUpdate    = true;
    }
    
    // Events
    public void onPositionChange( Vector2f oldValue, Vector2f newValue ) {
        
    }
    
    public void onRotationChange( float oldValue, float newValue ) {
        
    }
    
    public void onScaleChange( Vector2f oldValue, Vector2f newValue ) {
        
    }
    
    public void onOriginChange( Vector2f oldValue, Vector2f newValue ) {
        
    }
    
    public Transformable setPosition(float x, float y) {
        
        // Check if the position has changes
        if(mPositionX == x && mPositionY == y) {
            // No change
            return this;
        }
        
        onPositionChange( new Vector2f(mPositionX, mPositionY), new Vector2f(x,y) );
        
        // Update the position
        this.mPositionX = x;
        this.mPositionY = y;
        
        // We need to update our transform
        mTransformNeedUpdate = true;
        mInverseTransformNeedUpdate = true;
        
        return this;
    }
    
    public Transformable setPosition(Vector2f position) {
        return setPosition(position.x, position.y);
    }
    
    public Transformable setRotation(float rotation) {
        
        if(mRotation == rotation) {
            // No change
            return this;
        }
        
        onRotationChange( mRotation, rotation );
        
        mRotation = rotation;
        
        while(this.mRotation > 360.f) {
            this.mRotation -= 360.f;
        }
        while(this.mRotation > 360.f) {
            this.mRotation += 360.f;
        }
        
        this.mTransformNeedUpdate = true;
        this.mInverseTransformNeedUpdate = true;
        
        return this;
    }
    
    public Transformable setScale(float x, float y) {
        
        if(mScaleX == x && mScaleY == y) {
            return this;
        }
        
        onScaleChange( new Vector2f(mScaleX, mScaleY), new Vector2f(x,y) );
        
        this.mScaleX = x;
        this.mScaleY = y;
        
        this.mTransformNeedUpdate = true;
        this.mInverseTransformNeedUpdate = true;
        
        return this;
    }
    
    public Transformable setScale(Vector2f factors) {
        return this.setScale(factors.x, factors.y);
    }
    
    public Transformable setOrigin(float x, float y) {
        
        if(mOriginX == x && mOriginY == y) {
            return this;
        }
        
        onOriginChange( new Vector2f(mOriginX, mOriginY), new Vector2f(x,y) );
        
        this.mOriginX = x;
        this.mOriginY = y;
        
        this.mTransformNeedUpdate = true;
        this.mInverseTransformNeedUpdate = true;
        
        return this;
    }
    
    public Transformable setOriginCenter() {
        System.out.println("Transformable::setOriginCenter has not been implemented. Please @Override in a decendant class!");
        return this;
    }
    
    public Transformable setOrigin(Vector2f origin) {
        return this.setOrigin(origin.x, origin.y);
    }
    
    public Vector2f getPosition() {
        return new Vector2f( mPositionX, mPositionY );
    }
    
    public Vector2f getScale() {
        return new Vector2f( mScaleX, mScaleY );
    }
    
    public Vector2f getOrigin() {
        return new Vector2f(mOriginX, mOriginY);
    }
    
    public float getX() {
        return mPositionX;
    }
    
    public float getY() {
        return mPositionY;
    }
    
    public float getOriginX() {
        return mOriginX;
    }
    
    public float getOriginY() {
        return mOriginY;
    }
    
    public float getScaleX() {
        return mScaleX;
    }
    
    public float getScaleY() {
        return mScaleY;
    }
    
    public float getRotation() {
        return mRotation;
    }
    
    public Transformable rotate(float angle) {
        return setRotation( mRotation + angle );
    }
    
    public Transformable move(float x, float y) {
        return setPosition( mPositionX + x, mPositionY + y );
    }
    
    public Transformable move(Vector2f offset) {
        return move(offset.x, offset.y);
    }
    
    public Transformable scale(float x, float y) {
        return setScale( mScaleX + x, mScaleY + y );
    }
    
    public Transformable scale(Vector2f factor) {
        return scale(factor.x, factor.y);
    }
       
    public Transform getTransform() {
        
        if(mTransformNeedUpdate) {
            
            float angle     = -(float)Math.toRadians(mRotation);
            float cosine    = (float)Math.cos(angle);
            float sine      = (float)Math.sin(angle);
            float sxc       = mScaleX * cosine;
            float syc       = mScaleY * cosine;
            float sxs       = mScaleX * sine;
            float sys       = mScaleY * sine;
            float tx        = -mOriginX * sxc - mOriginY * sys + mPositionX;
            float ty        =  mOriginX * sxs - mOriginY * syc + mPositionY;
            
            mTransform = new Transform(  sxc, sys, tx,
                                        -sxs, syc, ty,
                                        0.f, 0.f, 1.f);
            
            mTransformNeedUpdate = false;
            
        }
        
        return mTransform;
        
    }
    
    public Transform getInverseTransform() {
        
        if(mInverseTransformNeedUpdate) {
            
            mInverseTransform = getTransform().getInverse();
            
            mInverseTransformNeedUpdate = false;
            
        }
        
        return mInverseTransform;
        
    }
       
}
