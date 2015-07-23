///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.main.Math;
//
//import java.awt.geom.AffineTransform;
//
///**
// *
// * @author Jack
// */
//public class BoxFloat {
//
//    public float x, y, width, height;
//    
//    public BoxFloat() {
//        x = y = width = height = 0.f;
//    }
//    
//    public BoxFloat(float x, float y) {
//        this();
//        this.x = x;
//        this.y = y;
//    }
//    
//    public BoxFloat(float x, float y, float width, float height) {
//        this.x = x;
//        this.y = y;
//        this.width = width;
//        this.height = height;
//    }
//    
//    public BoxFloat(Vector2f vec) {
//        this(vec.x,vec.y);
//    }
//    
//    public BoxFloat reset() {
//        x = y = width = height = 0.f;
//        return this;
//    }
//    
//    public BoxFloat set(float x, float y) {
//        reset();
//        this.x = x;
//        this.y = y;
//        return this;
//    }
//    
//    public BoxFloat set(float x, float y, float width, float height) {
//        this.x = x;
//        this.y = y;
//        this.width = width;
//        this.height = height;
//        return this;
//    }
//    
//    public BoxFloat set(Vector2f vec) {
//        return set(vec.x,vec.y);
//    }
//    
//    public BoxFloat setWidth(float width) {
//        this.width = width;
//        return this;
//    }
//    
//    public BoxFloat setHeight(float height) {
//        this.height = height;
//        return this;
//    }
//    
//    protected class MatrixInput {
//        double px, py, sx, sy, rot, ox, oy;
//    };
//    
//    public class Points {
//        Vector2d point[] = new Vector2d[4];
//    };
//    
//    public Points calculateMatrix(MatrixInput in) {
//        
//        double[] x,y;
//        x = new double[2];
//        y = new double[2];
//        
//        // Something to store the 4 points in
//        Points p = new Points();
//        
//        // Origin
//        x[0] = Point.inDirectionX(in.ox, in.rot) + Point.inDirectionX(in.oy, in.rot - 90.0);
//        y[0] = Point.inDirectionY(in.ox, in.rot) + Point.inDirectionY(in.oy, in.rot - 90.0);
//        
//        // Size
//        x[1] = Point.inDirectionX(in.sx - in.ox, in.rot) + Point.inDirectionX(in.sy - in.oy, in.rot - 90.0);
//        y[1] = Point.inDirectionY(in.sx - in.ox, in.rot) + Point.inDirectionY(in.sy - in.oy, in.rot - 90.0);
//        
//        // Top left
//        p.point = new Vector2d( in.px - x[0], in.py - y[0] );
//        
//        
//        return p;
//        
//    }
//    
//    public float getTop() {
//        return this.y;
//    }
//    
//    public float getLeft() {
//        return this.x;
//    }
//    
//    public float getRight() {
//        return this.x + Point.inDirectionX(width, width)
//    }
//    
//    public BoxFloat scale(float x, float y) {
//        
//        return this;
//    }
//    
//    public BoxFloat rotate(float angle, Vector2f offset) {
//        
//        AffineTransform topLeft = AffineTransform.getRotateInstance(left, top, offset.x, offset.y);
//        AffineTransform bottomRight = AffineTransform.getRotateInstance(right, bottom, offset.x, offset.y);
//        
//        topLeft.rotate(angle);
//        bottomRight.rotate(angle);
//        
//        this.left       = (float)topLeft.getTranslateX();
//        this.top        = (float)topLeft.getTranslateY();
//        this.right      = (float)bottomRight.getTranslateX();
//        this.bottom     = (float)bottomRight.getTranslateY();
//        
//        return this;
//    }
//    
//    public BoxFloat rotate(float angle) {
//        return rotate(angle, new Vector2f(this.left, this.top));
//    }
//    
//    public BoxFloat move(float x, float y) {
//        this.left += x;
//        this.top += y;
//        this.right += x;
//        this.bottom += y;
//        return this;
//    }
//    
//    public BoxFloat move(Vector2f offset) {
//        return move(offset.x,offset.y);
//    }
//    
//    public BoxFloat scale() {
//        
//    }
//    
//    public BoxFloat rotate() {
//        
//    }
//    
//}
