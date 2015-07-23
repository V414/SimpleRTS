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
    
    public float x, y, width, height;
    
    public BoundingBox() {
        x = y = width = height = 0.f;
    
    }    
    
    public BoundingBox(float left, float top, float width, float height) {
        this.x   = left;
        this.y    = top;
        this.width  = width;
        this.height = height;
    }
    
    public BoundingBox(BoundingBox other) {
        this.x = other.x;
        this.y = other.y;
        this.width = other.width;
        this.height = other.height;
    }
    
    public BoundingBox setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    public BoundingBox setPosition(Vector2f p) {
        this.x = p.x;
        this.y = p.y;
        return this;
    }
    
    public Vector2f getSize() {
        return new Vector2f(this.width, this.height);
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
        
    public BoundingBox move(Vector2f offset) {
        this.x += offset.x;
        this.y += offset.y;
        return this;
    } 
    
    public BoundingBox move(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }
    
    public BoundingBox rotate(float angle, Vector2f origin) {
        this.x += Point.inDirectionX(this.width + origin.x, angle);
        this.y += Point.inDirectionY(this.height + origin.y, angle);
        return this;
    }
    
    public BoundingBox rotate(float angle) {
        return rotate(angle, new Vector2f(0.f,0.f));
    }
    
    public BoundingBox rotateCenter(float angle) {
        return rotate(angle, new Vector2f(this.width / 2.f, this.height / 2.f));
    }
    
    public BoundingBox setRectangle(float left, float top, float right, float bottom) {
        
        Vector2f topLeft = new Vector2f( Math.min(left, right), Math.min( top, bottom ) );
        Vector2f bottomRight = new Vector2f( Math.max(left, right), Math.max( top, bottom ) );
        
        this.x = topLeft.x;
        this.y = topLeft.y;
        this.width = bottomRight.x - topLeft.x;
        this.height = bottomRight.y - topLeft.y;
     
        return this;
        
    }
    
    public BoundingBox setSize(Vector2f size) {
        this.width = size.x;
        this.height = size.y;
        return this;
    }
    
    public BoundingBox setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }
    
    public Vector2f getPosition() {
        return new Vector2f(this.x, this.y);
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    /**
     * Checks whether a Point (x,y) is within the bounding box.
     * @param x
     * @param y
     * @return True if the point is within the bounding box.
     */
    public boolean contains(float x, float y) {
        float minX = Math.min(this.x, this.x + this.width);
        float minY = Math.min(this.y, this.y + this.height);
        float maxX = Math.max(this.x, this.x + this.width);
        float maxY = Math.max(this.y, this.y + this.height);
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
    
    public boolean contains(Vector2d point) {
        return contains((float)point.x, (float)point.y);
    }
    
    /**
     * Checks whether all 4 points of a Box.Points structure are within this bounding box.
     * @param points
     * @return 
     */
    public boolean containsAll(Box.Points points) {
        return (
                    contains(points.point[0]) &&    // Top Left
                    contains(points.point[1]) &&    // Top Right
                    contains(points.point[2]) &&    // Bottom Right
                    contains(points.point[3])       // Bottom Left
                );
    }
    
    /**
     * Checks whether at least 1 point of a Box.Points structure are within this bounding box.
     * @param points
     * @return 
     */
    public boolean containsAny(Box.Points points) {
        return (
                    contains(points.point[0]) ||    // Top Left
                    contains(points.point[1]) ||    // Top Right
                    contains(points.point[2]) ||    // Bottom Right
                    contains(points.point[3])       // Bottom Left
                );
    }
    
    
    
    /**
     * Checks whether another box is within this bounding box.
     * @param other
     * @return 
     */
    public boolean contains(BoundingBox other) {
        
        return (
                
                    contains(other.x, other.y) &&
                    contains(other.x + other.width, other.y) &&
                    contains(other.x + other.width, other.y + other.height) &&
                    contains(other.x, other.y + other.height)
                
                );
        
    }
    
    // TODO: intersects

    public void setRectangle(Vector2f topLeft, Vector2f bottomRight) {
        setRectangle(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
    }
    
}
