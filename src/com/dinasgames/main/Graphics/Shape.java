/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Graphics;

import java.awt.Color;
import com.dinasgames.main.Math.Matrix3f;
import com.dinasgames.main.Math.Vector2f;

/**
 *
 * @author Jack
 */
public class Shape extends Renderable {
    
    /**
     * The outline color of the shape.
     */
    protected Color mOutlineColor;
    
   /**
     * The fill color of the shape.
     */
    protected Color mFillColor;
    
   /**
     * The thickness in pixels of the outline.
     */
    protected float mOutlineThickness;
    
   /**
     * The rotation in degrees of the shape.
     */
    protected float mRotation; 
    
    /**
     * The position of the shape in pixels.
     */
    protected Vector2f mPosition;
    
    /**
     * The size of the shape in pixels.
     */
    protected Vector2f mSize;
    
    /**
     * The scale of the shape.
     */
    protected Vector2f mScale;
    
    /**
     * The origin of the shape. The drawn position will be offset by this amount.
     */
    protected Vector2f mOrigin;

    /**
     * The transform for this shape. Used to calculate point positions when applying rotation, scale etc.
     */
    protected Matrix3f mTransform;
    
    /**
     * The inverse matrix of the last.
     */
    protected Matrix3f mInverseTransform;
    
    /**
     * Whether the transform needs to be recalculated. (Used for when rotation, scale etc. are changed.)
     */
    protected boolean mTransformNeedsUpdate;
    
    /**
     * Whether the inverse transform should be recalculated;
     */
    protected boolean mInverseTransformNeedsUpdate;
    
    protected Shape() {
        mOutlineColor = Color.black;
        mFillColor = Color.black;
        mOutlineThickness = 0.f;
        mPosition = new Vector2f(0.f, 0.f);
        mSize = new Vector2f(0.f, 0.f);
        mOrigin = new Vector2f(0.f,0.f);
        mScale = new Vector2f(1.f,1.f);
        mRotation = 0.f;
        mTransformNeedsUpdate = false;
    }
    
    public void recalculate() {
        
    }
    
    public Matrix3f getTransform() {
        
        if(mTransformNeedsUpdate) {
            
            float angle  = -mRotation * 3.141592654f / 180.f;
            float cosine = (float)Math.cos(angle);
            float sine   = (float)Math.sin(angle);
            float sxc    = mScale.x * cosine;
            float syc    = mScale.y * cosine;
            float sxs    = mScale.x * sine;
            float sys    = mScale.y * sine;
            float tx     = -mOrigin.x * sxc - mOrigin.y * sys + mPosition.x;
            float ty     =  mOrigin.x * sxs - mOrigin.y * syc + mPosition.y;
            
            mTransform = new Matrix3f( sxc, sys, tx,
                                        -sxs, syc, ty,
                                        0.f, 0.f, 1.f);
            
            mTransformNeedsUpdate = false;
        }
        
        return mTransform;
        
    }
    
    public Matrix3f getInverseTransform() {
        
        if(mInverseTransformNeedsUpdate) {
            
            mInverseTransform = getTransform();
            mInverseTransform.invert();
            mInverseTransformNeedsUpdate = false;
            
        }
        
        return mInverseTransform;
        
    }
    
    public float getRotation() {
        return mRotation;
    }
    
    public Vector2f getScale() {
        return mScale;
    }
    
    public Vector2f getOrigin() {
        return mOrigin;
    }
    
    public Shape setRotation(float rotation) {
        mRotation = rotation;
        while(mRotation < 0) {
            mRotation += 360;
        }
        while(mRotation > 360) {
            mRotation -= 360;
        }
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        return this;
    }
    
    public Shape setWidth(float width) {
        if(mSize.x == width) { return this; }
        mSize.x = width;
        return this;
    }
    
    public Shape setHeight(float height) {
        if(mSize.y == height) { return this; }
        mSize.y = height;
        recalculate();
        return this;
    }
    
    public Shape setPosition(Vector2f position) {
        if(mPosition.x == position.x && mPosition.y == position.y) {
            return this;
        }
        mPosition = position;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        recalculate();
        return this;
    }   
    
    public Shape setX(float x) {
        if(mPosition.x == x) {
            return this;
        }
        mPosition.x = x;
        recalculate();
        return this;
    }
    
    public Shape setY(float y) {
        if(mPosition.y == y) {
            return this;
        }
        mPosition.y = y;
        recalculate();
        return this;
    }
    
    public float getY() {
        return mPosition.y;
    }
    
    public float getX() {
        return mPosition.x;
    }
    
    public Shape setPosition(float x, float y) {
        if(mPosition.x == x && mPosition.y == y) {
            return this;
        }
        mPosition.x = x;
        mPosition.y = y;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        recalculate();
        return this;
    }
    
    public Shape setScale(Vector2f scale) {
        if(mScale.x == scale.x && mScale.y == scale.y) {
            return this;
        }
        mScale = scale;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        return this;
    }   
    
    public Shape setScale(float x, float y) {
        if(mScale.x == x && mScale.y == y) {
            return this;
        }
        mScale.x = x;
        mScale.y = y;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        return this;
    }
    
    public Shape setSize(Vector2f size) {
        if(mSize.x == size.x && mSize.y == size.y) {
            return this;
        }
        mSize = size;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        recalculate();
        return this;
    }   
    
    public Shape setSize(float x, float y) {
        if(mSize.x == x && mSize.y == y) {
            return this;
        }
        mSize.x = x;
        mSize.y = y;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        recalculate();
        return this;
    }
    
    public Shape setOrigin(Vector2f origin) {
        if(mOrigin.x == origin.x && mOrigin.y == origin.y) {
            return this;
        }
        mOrigin = origin;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        return this;
    }   
    
    public Shape setOrigin(float x, float y) {
        if(mOrigin.x == x && mOrigin.y == y) {
            return this;
        }
        mOrigin.x = x;
        mOrigin.y = y;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        return this;
    }
    
    public Shape setOriginCenter(){
        setOrigin(mSize.x / 2.f, mSize.y / 2.f);
        return this;
    }
    
    public Vector2f getPosition() {
        return mPosition;
    }
    
    public Vector2f getSize() {
        return mSize;
    }
    
    public float getWidth() {
        return mSize.x;
    }
    
    public float getHeight() {
        return mSize.y;
    }
    
    public Shape setOutlineColor(Color color) {
        mOutlineColor = color;
        return this;
    }
    
    public Shape setFillColor(Color color) {
        mFillColor = color;
        return this;
    }
    
    public Shape setOutlineThickness(float t) {
        mOutlineThickness = t;
        return this;
    }
    
//    @Override
//    public void render(Graphics2D g) {
//       
//        
//        g.setColor(mFillColor);
//        g.drawRect(x, y, width, height);
//        
//    }
    
}
