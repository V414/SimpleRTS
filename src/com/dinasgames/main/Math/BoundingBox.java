/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Math;

/**
 *
 * @author Jack
 */
public class BoundingBox {
    
    public float left, top, width, height;
    
    public BoundingBox() {
        left = top = width = height = 0.f;
    
    }    
    
    public BoundingBox(float left, float top, float width, float height) {
        this.left   = left;
        this.top    = top;
        this.width  = width;
        this.height = height;
    }
    
    public BoundingBox(BoundingBox other) {
        this.left = other.left;
        this.top = other.top;
        this.width = other.width;
        this.height = other.height;
    }
    
    public void setPosition(float x, float y) {
        this.left = x;
        this.top = y;
    }
    
    public void setPosition(Vector2f p) {
        this.left = p.x;
        this.top = p.y;
    }
    
    public void setRectangle(float left, float top, float right, float bottom) {
        
        Vector2f topLeft = new Vector2f( Math.min(left, right), Math.min( top, bottom ) );
        Vector2f bottomRight = new Vector2f( Math.max(left, right), Math.max( top, bottom ) );
        
        this.left = topLeft.x;
        this.top = topLeft.y;
        this.width = bottomRight.x - topLeft.x;
        this.height = bottomRight.y - topLeft.y;
        
    }
    
    public void setSize(Vector2f size) {
        this.width = size.x;
        this.height = size.y;
    }
    
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Checks whether a Point (x,y) is within the bounding box.
     * @param x
     * @param y
     * @return True if the point is within the bounding box.
     */
    public boolean contains(float x, float y) {
        float minX = Math.min(this.left, this.left + this.width);
        float minY = Math.min(this.top, this.top + this.height);
        float maxX = Math.max(this.left, this.left + this.width);
        float maxY = Math.max(this.top, this.top + this.height);
        return (x >= minX) && (y >= minY) && (x <= maxX) && (y <= maxY);
    }
    
    /**
     * Checks whether a point is within the bounding box.
     * @param point
     * @return True if the point is within the bounding box.
     */
    public boolean contains(Vector2f point) {
        return this.contains(point.x, point.y);
    }
    
    /**
     * Checks whether another box is within this bounding box.
     * @param other
     * @return 
     */
    public boolean contains(BoundingBox other) {
        
        return (
                
                    contains(other.left, other.top) &&
                    contains(other.left + other.width, other.top) &&
                    contains(other.left + other.width, other.top + other.height) &&
                    contains(other.left, other.top + other.height)
                
                );
        
    }
    
    // TODO: intersects

    public void setRectangle(Vector2f topLeft, Vector2f bottomRight) {
        setRectangle(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
    }
    
}
