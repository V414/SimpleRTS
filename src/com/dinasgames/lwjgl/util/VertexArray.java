/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.math.BoundingBox;
import com.dinasgames.main.math.Vector2f;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jack
 */
public class VertexArray {
    
    List<Vertex> mVerts;
    PrimitiveType mType;
    
    public VertexArray() {
        mVerts = new ArrayList();
        mType = PrimitiveType.Points;
    }
    
    public VertexArray(PrimitiveType type) {
        this();
        mType = type;
    }
    
    public VertexArray clear() {
        mVerts.clear();
        return this;
    }
    
    public int getVertexCount() {
        return mVerts.size();
    }
    
    public Vertex get(int idx) {
        return mVerts.get(idx);
    }
    
    public VertexArray resize(int size) {
        
        while(mVerts.size() > size) {
            mVerts.remove(mVerts.size()-1);
        }
        
        while(mVerts.size() < size) {
            mVerts.add(new Vertex());
        }
        
        return this;
        
    }
    
    public VertexArray append(Vertex vertex) {
        mVerts.add(vertex);
        return this;
    }
    
    public PrimitiveType getPrimitiveType() {
        return mType;
    }
    
    public VertexArray setPrimitiveType(PrimitiveType type) {
        mType = type;
        return this;
    }
    
    public BoundingBox getBounds() {
        
        if(mVerts == null | mVerts.size() == 0) {
            return new BoundingBox();
        }
        
        float left   = mVerts.get(0).x;
        float top    = mVerts.get(0).y;
        float right  = mVerts.get(0).x;
        float bottom = mVerts.get(0).y;

        for (int i = 1; i < mVerts.size(); ++i)
        {
           Vector2f position = new Vector2f(mVerts.get(i).x, mVerts.get(i).y);

           // Update left and right
           if (position.x < left) {
               left = position.x;
           }else if (position.x > right) {
               right = position.x;
           }
           
           // Update top and bottom
           if (position.y < top) {
               top = position.y;
           } else if (position.y > bottom) {
               bottom = position.y;
           }
        }

        return new BoundingBox(left, top, right - left, bottom - top);
        
    }
    
    public VertexArray draw(RenderTarget target, RenderStates states) {
        
        if(mVerts == null || mVerts.size() == 0) {
            return this;
        }
        
        Vertex v[] = new Vertex[mVerts.size()];
        mVerts.toArray(v);
        
        target.draw(v, mType, states);
        
        return this;
        
    }
    
    public Vertex[] toArray() {
        
        Vertex[] cpy = new Vertex[mVerts.size()];
        
        for(int i = 0; i < mVerts.size(); i++) {
            cpy[i] = mVerts.get(i);
        }
        
        return cpy;
    }
    
}
