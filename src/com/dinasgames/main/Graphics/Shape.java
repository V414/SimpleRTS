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
    
    private Shape self() {
        return (Shape)Renderer.getCurrent().get(mID);
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
        if(inRenderQueue()) {
            return self().getRotation();
        }
        return mRotation;
    }
    
    public Vector2f getScale() {
        if(inRenderQueue()) {
            return self().getScale();
        }
        return mScale;
    }
    
    public Vector2f getOrigin() {
        if(inRenderQueue()) {
            return self().getOrigin();
        }
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
        if(inRenderQueue()) {
            self().setRotation(rotation);
        }
        return this;
    }
    
    public Shape setWidth(float width) {
        mSize.x = width;
        if(inRenderQueue()) {
            self().setWidth(width);
        }
        return this;
    }
    
    public Shape setHeight(float height) {
        mSize.y = height;
        if(inRenderQueue()) {
            self().setHeight(height);
        }
        return this;
    }
    
    public Shape setPosition(Vector2f position) {
        mPosition = position;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        recalculate();
        if(inRenderQueue()) {
            self().setPosition(position);
        }
        return this;
    }   
    
    public Shape setPosition(float x, float y) {
        mPosition.x = x;
        mPosition.y = y;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        recalculate();
        if(inRenderQueue()) {
            self().setPosition(x, y);
        }
        return this;
    }
    
    public Shape setScale(Vector2f scale) {
        mScale = scale;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        if(inRenderQueue()) {
            self().setScale(scale);
        }
        return this;
    }   
    
    public Shape setScale(float x, float y) {
        mScale.x = x;
        mScale.y = y;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        if(inRenderQueue()) {
            self().setScale(x, y);
        }
        return this;
    }
    
    public Shape setSize(Vector2f size) {
        mSize = size;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        recalculate();
        if(inRenderQueue()) {
            self().setSize(size);
        }
        return this;
    }   
    
    public Shape setSize(float x, float y) {
        mSize.x = x;
        mSize.y = y;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        recalculate();
        if(inRenderQueue()) {
            self().setSize(x, y);
        }
        return this;
    }
    
    public Shape setOrigin(Vector2f origin) {
        mOrigin = origin;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        if(inRenderQueue()) {
            self().setOrigin(origin);
        }
        return this;
    }   
    
    public Shape setOrigin(float x, float y) {
        mOrigin.x = x;
        mOrigin.y = y;
        mTransformNeedsUpdate = true;
        mInverseTransformNeedsUpdate = true;
        if(inRenderQueue()) {
            self().setOrigin(x, y);
        }
        return this;
    }
    

    
    public Vector2f getPosition() {
        if(inRenderQueue()) {
            return self().getPosition();
        }
        return mPosition;
    }
    
    public Vector2f getSize() {
        if(inRenderQueue()) {
            return self().getSize();
        }
        return mSize;
    }
    
    public float getWidth() {
        if(inRenderQueue()) {
            return self().getWidth();
        }
        return mSize.x;
    }
    
    public float getHeight() {
        if(inRenderQueue()) {
            return self().getHeight();
        }
        return mSize.y;
    }
    
    public void setOutlineColor(Color color) {
        mOutlineColor = color;
        if(inRenderQueue()) {
            self().setOutlineColor(color);
        }
    }
    
    public void setFillColor(Color color) {
        mFillColor = color;
        if(inRenderQueue()) {
            self().setFillColor(color);
        }
    }
    
    public void setOutlineThickness(float t) {
        mOutlineThickness = t;
        if(inRenderQueue()) {
            self().setOutlineThickness(t);
        }
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
