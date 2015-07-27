/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.math.BoundingBox;
import com.dinasgames.main.math.Vector2f;

/**
 *
 * @author Jack
 */
public class View {
    
    protected float         mCenterX, mCenterY, mSizeX, mSizeY, mRotation;
    protected BoundingBox   mViewport;
    protected Transform     mTransform;
    protected Transform     mInverseTransform;
    protected boolean       mTransformUpdated;
    protected boolean       mInverseTransformUpdated;
    
    public View() {
        mCenterX = mCenterY = mSizeX = mSizeY = mRotation = 0;
        mViewport = new BoundingBox(0.f, 0.f, 1.f, 1.f);
        mTransformUpdated = mInverseTransformUpdated = false;
        reset(new BoundingBox(0.f,0.f,1000.f,1000.f));
    }
    
    public View(BoundingBox rectangle) {
        mCenterX = mCenterY = mSizeX = mSizeY = mRotation = 0;
        mViewport = new BoundingBox(0.f, 0.f, 1.f, 1.f);
        mTransformUpdated = mInverseTransformUpdated = false;
        reset(rectangle);
    }
    
    public View(float left, float top, float width, float height) {
        
        this();
        
        mCenterX = left + width / 2.f;
        mCenterY = top + height / 2.f;
        
        mSizeX = width;
        mSizeY = height;
        
    }
    
    public View(Vector2f center, Vector2f size) {
        
        this(center.x, center.y, size.x, size.y);
        
    }
    
    public View(View other) {
        
        this();
        
        Vector2f oc = other.getCenter();
        Vector2f os = other.getSize();
        
        mCenterX = oc.x;
        mCenterY = oc.y;
        mSizeX = os.x;
        mSizeY = os.y;
        mRotation = other.getRotation();
        mViewport = other.getViewport();
        
    }
    
    public View setCenter(float x, float y) {
        
        mCenterX = x;
        mCenterY = y;
        
        mTransformUpdated    = false;
        mInverseTransformUpdated = false;
        
        return this;
    }
    
    public View setCenter(Vector2f center) {
        return setCenter(center.x, center.y);
    }
    
    public View setSize(float width, float height) {
        
        mSizeX = width;
        mSizeY = height;
        
        mTransformUpdated    = false;
        mInverseTransformUpdated = false;
        
        return this;
    }
    
    public View setSize(Vector2f size) {
        return setSize(size.x, size.y);
    }
    
    public View setRotation(float angle) {
        
        this.mRotation = angle;
        while(this.mRotation > 360.f) {
            this.mRotation -= 360.f;
        }
        while(this.mRotation > 360.f) {
            this.mRotation += 360.f;
        }
        
        mTransformUpdated    = false;
        mInverseTransformUpdated = false;
        
        return this;
    }
    
    public View setViewport(BoundingBox box) {
        mViewport = box;
        return this;
    }
    
    public View reset(float left, float top, float width, float height) {
        
        mCenterX = left + width / 2.f;
        mCenterY = top + height / 2.f;
        
        mSizeX = width;
        mSizeY = height;
        
        mTransformUpdated    = false;
        mInverseTransformUpdated = false;
        
        return this;
        
    }
    
    public View reset(BoundingBox box) {
        
        return reset(box.x, box.y, box.width, box.height);
        
    }
    
    public Vector2f getCenter() {
        return new Vector2f(mCenterX, mCenterY);
    }
    
    public Vector2f getPosition() {
        return new Vector2f( mCenterX - mSizeX / 2.f, mCenterY - mSizeY / 2.f );
    }
    
    public Vector2f getSize() {
        return new Vector2f(mSizeX, mSizeY);
    }
    
    public float getRotation() {
        return mRotation;
    }
    
    public BoundingBox getViewport() {
        return new BoundingBox(mViewport);
    }
    
    public View move(float x, float y) {
        return setCenter( mCenterX + x, mCenterY + y );
    }
    
    public View move(Vector2f offset) {
        return move(offset.x, offset.y);
    }
    
    public View rotate(float angle) {
        return setRotation(mRotation + angle);
    }
    
    public View zoom(float factor) {
        return setSize( mSizeX * factor, mSizeY * factor );
    }
    
    public Transform getTransform() {
        
         // Recompute the matrix if needed
        if (!mTransformUpdated)
        {
            // Rotation components
            float angle  =  (float)Math.toRadians(mRotation); //mRotation * 3.141592654f / 180.f;
            float cosine = (float)Math.cos(angle);
            float sine   = (float)Math.sin(angle);
            float tx     = -mCenterX * cosine - mCenterY * sine + mCenterX;
            float ty     =  mCenterX * sine - mCenterY * cosine + mCenterY;

            // Projection components
            float a =  2.f / mSizeX;
            float b = -2.f / mSizeY;
            float c = -a * mCenterX;
            float d = -b * mCenterY;

            // Rebuild the projection matrix
            mTransform = new Transform( a * cosine, a * sine,   a * tx + c,
                                    -b * sine,   b * cosine, b * ty + d,
                                     0.f,        0.f,        1.f);
            
            mTransformUpdated = true;
        }

        return mTransform;
        
    }
    
    public Transform getInverseTransform() {
        
        // Recompute the matrix if needed
        if (!mInverseTransformUpdated)
        {
            mInverseTransform = getTransform().getInverse();
            mInverseTransformUpdated = true;
        }

        return mInverseTransform;
        
    }
    
    /**
     * Returns whether the point (x,y) are within the view.
     * @param x
     * @param y
     * @return 
     */
    public boolean contains(float x, float y) {
        
        float left = this.mCenterX - this.mSizeX / 2.f;
        float top = this.mCenterY - this.mSizeY / 2.f;
        float right = this.mCenterX + this.mSizeX / 2.f;
        float bottom = this.mCenterY + this.mSizeY / 2.f;
        
        return ( x >= left && x <= right && y >= top && y <= bottom );
        
    }
    
    /**
     * Returns whether the point is within the view.
     * @param point
     * @return 
     */
    public boolean contains(Vector2f point) {
        return contains(point.x, point.y);
    }
    
    /**
     * Returns whether the box is touching the view.
     * @param box
     * @return 
     */
    public boolean intersects(BoundingBox box) {
        return (  
                    contains(box.x, box.y) ||                           // Top left
                    contains(box.x + box.width, box.y) ||               // Top right
                    contains(box.x + box.width, box.y + box.height) ||  // Bottom right
                    contains(box.x, box.y + box.height)                 // Bottom left
                );
    }
    
    /**
     * Returns whether the whole box is within the view.
     * @param box
     * @return 
     */
    public boolean contains(BoundingBox box) {
        return (  
                    contains(box.x, box.y) &&                           // Top left
                    contains(box.x + box.width, box.y) &&               // Top right
                    contains(box.x + box.width, box.y + box.height) &&  // Bottom right
                    contains(box.x, box.y + box.height)                 // Bottom left
                );
    }
    
}
