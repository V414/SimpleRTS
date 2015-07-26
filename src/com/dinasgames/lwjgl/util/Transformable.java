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
public class Transformable {
    
    protected Vector2f mOrigin, mPosition, mScale;
    protected float mRotation;
    protected Transform mTransform, mInverseTransform;
    protected boolean mTransformNeedUpdate, mInverseTransformNeedUpdate;
    
    public Transformable() {
        this.mOrigin                        = new Vector2f(0.f, 0.f);
        this.mPosition                      = new Vector2f(0.f, 0.f);
        this.mScale                         = new Vector2f(1.f, 1.f);
        this.mRotation                      = 0.f;
        this.mTransform                     = new Transform();
        this.mInverseTransform              = new Transform();
        this.mTransformNeedUpdate           = true;
        this.mInverseTransformNeedUpdate    = true;
    }
    
    public Transformable setPosition(float x, float y) {
        this.mPosition.x = x;
        this.mPosition.y = y;
        mTransformNeedUpdate = true;
        mInverseTransformNeedUpdate = true;
        return this;
    }
    
    public Transformable setPosition(Vector2f position) {
        return setPosition(position.x, position.y);
    }
    
    public Transformable setRotation(float rotation) {
        this.mRotation = rotation;
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
        this.mScale.x = x;
        this.mScale.y = y;
        this.mTransformNeedUpdate = true;
        this.mInverseTransformNeedUpdate = true;
        return this;
    }
    
    public Transformable setScale(Vector2f factors) {
        return this.setScale(factors.x, factors.y);
    }
    
    public Transformable setOrigin(float x, float y) {
        this.mOrigin.x = x;
        this.mOrigin.y = y;
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
        return mPosition;
    }
    
    public Vector2f getScale() {
        return mScale;
    }
    
    public Vector2f getOrigin() {
        return mScale;
    }
    
    public float getRotation() {
        return mRotation;
    }
    
    public Transformable rotate(float angle) {
        mRotation += angle;
        this.mTransformNeedUpdate = true;
        this.mInverseTransformNeedUpdate = true;
        return this;
    }
    
    public Transformable move(float x, float y) {
        mPosition.x += x;
        mPosition.y += y;
        this.mTransformNeedUpdate = true;
        this.mInverseTransformNeedUpdate = true;
        return this;
    }
    
    public Transformable move(Vector2f offset) {
        return move(offset.x, offset.y);
    }
    
    public Transformable scale(float x, float y) {
        mScale.x += x;
        mScale.y += y;
        this.mTransformNeedUpdate = true;
        this.mInverseTransformNeedUpdate = true;
        return this;
    }
    
    public Transformable scale(Vector2f factor) {
        return scale(factor.x, factor.y);
    }
       
    public Transform getTransform() {
        if(mTransformNeedUpdate) {
            
            float angle     = -(float)Math.toRadians(mRotation);
            float cosine    = (float)Math.cos(angle);
            float sine      = (float)Math.sin(angle);
            float sxc       = mScale.x * cosine;
            float syc       = mScale.y * cosine;
            float sxs       = mScale.x * sine;
            float sys       = mScale.y * sine;
            float tx        = -mOrigin.x * sxc - mOrigin.y * sys + mPosition.x;
            float ty        =  mOrigin.x * sxs - mOrigin.y * syc + mPosition.y;
            
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
