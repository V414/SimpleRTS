/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

/**
 *
 * @author Jack
 */
public class VertexCache {
    
    protected PrimitiveType mType;
    protected FloatBuffer mVertexBuffer, mColorBuffer, mTexcoordsBuffer;
    protected int mSize;
    
    public VertexCache() {
        mSize = 0;
        mType = PrimitiveType.Points;
    }
    
    public VertexCache(PrimitiveType type) {
        mSize = 0;
        mType = type;
    }
    
    public PrimitiveType getType() {
        return mType;
    }
    
    public int getSize() {
        return mSize;
    }
    
    protected void create(int size, boolean init) {
        
        mVertexBuffer = BufferUtils.createFloatBuffer(size * 2);
        mColorBuffer = BufferUtils.createFloatBuffer(size * 4);
        mTexcoordsBuffer = BufferUtils.createFloatBuffer(size * 2);
        
        if(init) {
            int i = 0;
            while(i < size) {
                
                mVertexBuffer.put(0);
                mVertexBuffer.put(0);
                
                mTexcoordsBuffer.put(0);
                mTexcoordsBuffer.put(0);
                
                mColorBuffer.put(0);
                mColorBuffer.put(0);
                mColorBuffer.put(0);
                mColorBuffer.put(0);
                
                i++;
            }
            mVertexBuffer.flip();
            mColorBuffer.flip();
            mTexcoordsBuffer.flip();
        }
        
    }
    
    public VertexCache resize(int size) {
        return resize(size, true);
    }
    
    public VertexCache resize(int size, boolean keepOldData) {
        
        // Same size
        if(size == mSize) {
            return this;
        }
        
        // If we don't need to copy old data, just create the new buffers
        if(!keepOldData) {
            create(size, true);
            return this;
        }
        
        // Keep track of old buffers so we can copy existing data
        FloatBuffer oldVertexBuffer = mVertexBuffer;
        FloatBuffer oldColorBuffer = mColorBuffer;
        FloatBuffer oldTexcoordsBuffer = mTexcoordsBuffer;
        
        // Create the new buffers
        create(size, false);
        
        // Copy any required data
        int i = 0;
        while(i < size) {
            
            if(i < mSize) {
                
                // Copy old data
                mVertexBuffer.put(oldVertexBuffer.get());
                mVertexBuffer.put(oldVertexBuffer.get());
                
                mTexcoordsBuffer.put(oldTexcoordsBuffer.get());
                mTexcoordsBuffer.put(oldTexcoordsBuffer.get());
                
                mColorBuffer.put(oldColorBuffer.get());
                mColorBuffer.put(oldColorBuffer.get());
                mColorBuffer.put(oldColorBuffer.get());
                mColorBuffer.put(oldColorBuffer.get());
                
            }else{
                
                // Create new data
                mVertexBuffer.put(0.f); // X
                mVertexBuffer.put(0.f); // Y
                
                mTexcoordsBuffer.put(0);
                mTexcoordsBuffer.put(0);
                
                mColorBuffer.put(0.f); // R
                mColorBuffer.put(0.f); // G
                mColorBuffer.put(0.f); // B
                mColorBuffer.put(0.f); // A
                
            }
            
            i++;
            
        }
        
        mVertexBuffer.flip();
        mColorBuffer.flip();
        mTexcoordsBuffer.flip();
        
        // Apply new size
        mSize = size;
        
        return this;
        
    }
    
    public VertexCache setTexcoord( int idx, float x, float y ) {
        
        if(idx < 0 || idx >= mSize) {
            return this;
        }
        
        mTexcoordsBuffer.put((idx * 2) + 0, x);
        mTexcoordsBuffer.put((idx * 2) + 1, y);
        
        return this;
        
    }
    
    public VertexCache set( int idx, float x, float y, GLColor color ) {
        
        if(idx < 0 || idx >= mSize) {
            return this;
        }
        
        // Insert position
        mVertexBuffer.put((idx * 2) + 0, x);
        mVertexBuffer.put((idx * 2) + 1, y);
        
        // Insert color
        mColorBuffer.put((idx * 4) + 0, color.getRed());
        mColorBuffer.put((idx * 4) + 1, color.getGreen());
        mColorBuffer.put((idx * 4) + 2, color.getBlue());
        mColorBuffer.put((idx * 4) + 3, color.getAlpha());
        
        return this;
        
    }
    
    public VertexCache set( int idx, float x, float y, Color color ) {
        return set( idx, x, y, color.toGLColor() );
    }
    
    public VertexCache setPosition( int idx, float x, float y ) {
        
        if(idx < 0 || idx >= mSize) {
            return this;
        }
        
        mVertexBuffer.put((idx * 2) + 0, x);
        mVertexBuffer.put((idx * 2) + 1, y);
        
        return this;
        
    }
    
    public VertexCache setColor( int idx, GLColor color ) {
        
        if(idx < 0 || idx >= mSize) {
            return this;
        }
        
        mColorBuffer.put((idx * 4) + 0, color.getRed());
        mColorBuffer.put((idx * 4) + 1, color.getGreen());
        mColorBuffer.put((idx * 4) + 2, color.getBlue());
        mColorBuffer.put((idx * 4) + 3, color.getAlpha());
        
        return this;
        
    }
    
    public VertexCache setColor(int idx, Color color) {
        return setColor(idx, color.toGLColor());
    }
    
    public FloatBuffer getVertexBuffer() {
        return mVertexBuffer;
    }
    
    public FloatBuffer getColorBuffer() {
        return mColorBuffer;
    }
    
    public FloatBuffer getTexcoordsBuffer() {
        return mTexcoordsBuffer;
    }
    
}
