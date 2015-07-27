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
public class Shape extends Renderable {

    protected VertexCache mShapeCache, mOutlineCache;
    protected Color mFillColor, mOutlineColor;
    protected float mOutlineThickness;
    protected VertexArray mVerts;
    protected VertexArray mOutlineVerts;
    protected BoundingBox mInsideBounds, mBounds, mTextureRect;
    protected Texture mTexture;
    
    public Shape() {
        
        mFillColor = new Color(255, 255, 255);
        mOutlineColor = new Color(255, 255, 255);
        mVerts = new VertexArray(PrimitiveType.TrianglesFan);
        mOutlineVerts = new VertexArray(PrimitiveType.TrianglesStrip);
        mInsideBounds = new BoundingBox();
        mBounds = new BoundingBox();
        mTextureRect = new BoundingBox();
        mOutlineThickness = 0;
        mShapeCache = new VertexCache(PrimitiveType.TrianglesFan);
        mOutlineCache = new VertexCache(PrimitiveType.TrianglesStrip);
        mTexture = null;
    }
    
    // Events
    public void onTextureChange( Texture oldValue, Texture newValue ) {
        
    }
    
    public void onTextureRectChange( BoundingBox oldValue, BoundingBox newValue ) {
        
    }
    
    public void onFillColorChange( Color oldValue, Color newValue ) {
    }
    
    public void onOutlineColorChange( Color oldValue, Color newValue ) {
        
    }
    
    public void onOutlineThicknessChange( float oldValue, float newValue ) {
        
    }
    
    public BoundingBox getTextureRect() {
        return mTextureRect;
    }
    
    public Texture getTexture() {
        return mTexture;
    }
    
    public Shape setTextureRect(BoundingBox rect) {
        
        if(mTextureRect.equals(rect)) {
            return this;
        }
        
        onTextureRectChange( mTextureRect, rect );
        mTextureRect = rect;
        
        updateTexCoords();

        return this;
    }
    
    public Shape setTexture(Texture texture) {
        return setTexture(texture, false);
    }
    
    public Shape setTexture(Texture texture, boolean resetRect) {
        
        if( texture == null ) {
            if(mTexture != null) {
                onTextureChange( mTexture, texture );
                mTexture = null;
            }
            return this;
        }
        
        if(mTexture != null && mTexture.equals(texture)) {
            return this;
        }
        
        if(resetRect || (mTexture == null && (mTextureRect.equals(new BoundingBox())))) {
            setTextureRect(new BoundingBox( 0, 0, texture.getWidth(), texture.getHeight() ));
        }
        
        onTextureChange( mTexture, texture );
        mTexture = texture;
        
        return this;
        
    }
    
    @Override
    public Shape setOriginCenter() {
        System.out.println("Shape::setOriginCenter has not been implemented. Please @Override in a decendant class!");
        return this;
    }
    
    public Shape setFillColor(Color color) {
        
        if(mFillColor.equals(color)) {
            return this;
        }
        
        onFillColorChange( mFillColor, color );
        mFillColor = color;
        updateFillColors();
        
        return this;
    }
    
    public Color getFillColor() {
        return new Color(mFillColor);
    }
    
    public Shape setOutlineColor(Color color) {
        
        if(mOutlineColor.equals(color)) {
            return this;
        }
        
        onOutlineColorChange( mOutlineColor, color );
        mOutlineColor = color;
        updateOutlineColors();
        
        return this;
    }
    
    public Color getOutlineColor() {
        return new Color(mOutlineColor);
    }
    
    public Shape setOutlineThickness(float thickness) {
        
        if(mOutlineThickness == thickness) {
            return this;
        }
        
        onOutlineThicknessChange( mOutlineThickness, thickness );
        mOutlineThickness = thickness;
        update();
        
        return this;
    }
    
    public float getOutlineThickness() {
        return mOutlineThickness;
    }
    
    public BoundingBox getLocalBounds() {
        return new BoundingBox(mBounds);
    }
    
    public BoundingBox getGlobalBounds() {
        //return new BoundingBox(mBounds);
        return getTransform().transformRect(getLocalBounds());
    }
    
    public Vector2f getPoint(int idx) {
        return null;
    }
    
    public int getPointCount() {
        return 0;
    }
    
    protected void update() {
        
        int count = getPointCount();
        if(count < 3) {
            mVerts.resize(0);
            mOutlineVerts.resize(0);
            return;
        }
        
        mVerts.resize(count + 2);
        mShapeCache.resize(count + 2);
        
        for(int i = 0; i < count; ++i) {
            Vector2f p = getPoint(i);
            Vertex v = mVerts.get(i+1);
            mVerts.get(i+1).x = p.x;
            mVerts.get(i+1).y = p.y;
        }
        
        Vertex firstPoint = mVerts.get(1);
        
        Vertex lastPoint = mVerts.get(count+1);
        lastPoint.x = firstPoint.x;
        lastPoint.y = firstPoint.y;
        
        Vertex zeroPoint = mVerts.get(0);

        zeroPoint.x = firstPoint.x;
        zeroPoint.x = firstPoint.x;
        
        mInsideBounds = mVerts.getBounds();
        
        zeroPoint.x = mInsideBounds.x + mInsideBounds.width / 2.f;
        zeroPoint.y = mInsideBounds.y + mInsideBounds.height / 2.f;
     
        updateFillColors();
        
        updateTexCoords();
        
        updateOutline();
        
        // Update the cache
        for(int i = 0; i < mVerts.getVertexCount(); i++) {
            Vertex v = mVerts.get(i);
            mShapeCache.set(i, v.x, v.y, v.color);
        }
        
    }
    
    @Override
    public void draw(RenderTarget target, RenderStates states) {
        
        RenderStates copy = new RenderStates(states);
        
        copy.transform.multiply(getTransform());
        
        //target.draw(mVerts, states);
        copy.texture = mTexture;
        target.draw(mShapeCache, copy);
        
        if(mOutlineThickness != 0) {
            //states.texture = null;
            //target.draw(mOutlineVerts, states);
            copy.texture = null;
            target.draw(mOutlineCache, copy);
        }
        
    }
    
    protected void updateTexCoords() {
        
        for(int i = 0; i < mVerts.getVertexCount(); i++) {
            Vertex v = mVerts.get(i);
            float xratio = (mInsideBounds.width > 0 ? (v.x - mInsideBounds.x) / mInsideBounds.width : 0.f);
            float yratio = (mInsideBounds.height > 0 ? (v.y - mInsideBounds.y) / mInsideBounds.height : 0.f);
            v.tx = mTextureRect.x + mTextureRect.width * xratio;
            v.ty = mTextureRect.y + mTextureRect.height * yratio;
            mShapeCache.setTexcoord( i, v.tx, v.ty );
        }
        
    }
    
    protected void updateFillColors() {
        for(int i = 0; i < mVerts.getVertexCount(); i++) {
            mVerts.get(i).color = mFillColor;
            mShapeCache.setColor( i, mFillColor );
        }
    }
    
    protected void updateOutlineColors() {
        for(int i = 0; i < mOutlineVerts.getVertexCount(); i++) {
            mOutlineVerts.get(i).color = mOutlineColor;
            mOutlineCache.setColor( i, mFillColor );
        }
    }
    
    protected void updateOutline() {
        
        int count = mVerts.getVertexCount() - 2;
        mOutlineVerts.resize((count + 1) * 2);
        mOutlineCache.resize((count +1) * 2);
        
        for(int i = 0; i < count; i++) {
            
            int index = i+1;
            
            Vector2f p0, p1, p2;
            p0 = new Vector2f();
            p1 = new Vector2f();
            p2 = new Vector2f();
            
            if(i == 0) {
                Vertex lastPoint = mVerts.get(count);
                p0.x = lastPoint.x;
                p0.y = lastPoint.y;
            }else{
                Vertex lastPoint = mVerts.get(index - 1);
                p0.x = lastPoint.x;
                p0.y = lastPoint.y;
            }

            Vertex current, next;
            current = mVerts.get(index);
            next = mVerts.get(index + 1);
            
            p1.x = current.x;
            p1.y = current.y;
            
            p2.x = next.x;
            p2.y = next.y;
            
            Vector2f n1 = computeNormal(p0,p1);
            Vector2f n2 = computeNormal(p1,p2);
            
            Vertex zeroPoint = mVerts.get(0);
            
            if(dotProduct(n1, Vector2f.subtract( new Vector2f(zeroPoint.x, zeroPoint.y), p1 )) > 0) {
                n1.negate();
            }
            
            if(dotProduct(n2, Vector2f.subtract( new Vector2f(zeroPoint.x, zeroPoint.y), p1 )) > 0) {
                n2.negate();
            }
            
            float factor = 1.f + (n1.x * n2.x + n1.y * n2.y);
            Vector2f normal = Vector2f.add(n1, n2).divide(factor);
            
            Vertex a = mOutlineVerts.get(i * 2 + 0);
            Vertex b = mOutlineVerts.get(i * 2 + 1);
            Vector2f c = new Vector2f(normal).multiply(mOutlineThickness).add(p1);
            
            a.x = p1.x;
            a.y = p1.y;
            
            b.x = c.x;
            b.y = c.y;
            
        }
        
        Vertex a = mOutlineVerts.get(count * 2 + 0);
        Vertex b = mOutlineVerts.get(count * 2 + 1);
        Vertex c = mOutlineVerts.get(0);
        Vertex d = mOutlineVerts.get(1);
        
        a.x = c.x;
        a.y = c.y;
        
        b.x = d.x;
        b.y = d.y;
        
        updateOutlineColors();
        
        // Update cache
        for(int i = 0; i < mOutlineVerts.getVertexCount(); i++) {
            Vertex v = mOutlineVerts.get(i);
            mOutlineCache.set(i, v.x, v.y, v.color);
        }
        
        mBounds = mOutlineVerts.getBounds();
        
    }
    
    protected Vector2f computeNormal(Vector2f p1, Vector2f p2)
    {
        Vector2f normal = new Vector2f( p1.y - p2.y, p2.x - p1.x);
        float length = (float)Math.sqrt(normal.x * normal.x + normal.y * normal.y);
        
        if(length != 0.f) {
            normal.divide(length);
        }
        
        return normal;
    }

    // Compute the dot product of two vectors
    protected float dotProduct(Vector2f p1, Vector2f p2)
    {
        return p1.x * p2.x + p1.y * p2.y;
    }
    
    @Override
    public boolean inView(View view) {
        BoundingBox bounds = new BoundingBox(getGlobalBounds());
        return view.intersects(bounds);
    }
    
}
