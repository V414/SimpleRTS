/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.Math.BoundingBox;
import com.dinasgames.main.Math.Vector2f;

/**
 *
 * @author Jack
 */
public class Transform {
    
    public static final Transform Identity = new Transform();
    
    protected float mMatrix[];
    
    public Transform() {
        mMatrix = new float[16];
        mMatrix[0] = 1.f; mMatrix[4] = 0.f; mMatrix[8]  = 0.f; mMatrix[12] = 0.f;
        mMatrix[1] = 0.f; mMatrix[5] = 1.f; mMatrix[9]  = 0.f; mMatrix[13] = 0.f;
        mMatrix[2] = 0.f; mMatrix[6] = 0.f; mMatrix[10] = 1.f; mMatrix[14] = 0.f;
        mMatrix[3] = 0.f; mMatrix[7] = 0.f; mMatrix[11] = 0.f; mMatrix[15] = 1.f;
    }
    
    public Transform(float a00, float a01, float a02,
                     float a10, float a11, float a12,
                     float a20, float a21, float a22) {
        mMatrix = new float[16];
        mMatrix[0] = a00; mMatrix[4] = a01; mMatrix[8]  = 0.f; mMatrix[12] = a02;
        mMatrix[1] = a10; mMatrix[5] = a11; mMatrix[9]  = 0.f; mMatrix[13] = a12;
        mMatrix[2] = 0.f; mMatrix[6] = 0.f; mMatrix[10] = 1.f; mMatrix[14] = 0.f;
        mMatrix[3] = a20; mMatrix[7] = a21; mMatrix[11] = 0.f; mMatrix[15] = a22;
    }
    
    public Transform(Transform other) {
        float[] otherMatrix = other.getMatrix();
        mMatrix = new float[16];
        for(int i = 0; i < 16; i ++) {
            mMatrix[i] = otherMatrix[i];
        }
    }
    
    public Transform set(float a00, float a01, float a02,
                        float a10, float a11, float a12,
                        float a20, float a21, float a22) {
        mMatrix[0] = a00; mMatrix[4] = a01; mMatrix[8]  = 0.f; mMatrix[12] = a02;
        mMatrix[1] = a10; mMatrix[5] = a11; mMatrix[9]  = 0.f; mMatrix[13] = a12;
        mMatrix[2] = 0.f; mMatrix[6] = 0.f; mMatrix[10] = 1.f; mMatrix[14] = 0.f;
        mMatrix[3] = a20; mMatrix[7] = a21; mMatrix[11] = 0.f; mMatrix[15] = a22;
        return this;
    }
    
    public float[] getMatrix() {
        return mMatrix;
    }
    
    public Transform getInverse() {
        
        float det = mMatrix[0] * (mMatrix[15] * mMatrix[5] - mMatrix[7] * mMatrix[13]) -
                    mMatrix[1] * (mMatrix[15] * mMatrix[4] - mMatrix[7] * mMatrix[12]) +
                    mMatrix[3] * (mMatrix[13] * mMatrix[4] - mMatrix[5] * mMatrix[12]);
        
        if(det != 0.f) {
            return new Transform( (mMatrix[15] * mMatrix[5] - mMatrix[7] * mMatrix[13]) / det,
                                    -(mMatrix[15] * mMatrix[4] - mMatrix[7] * mMatrix[12]) / det,
                                     (mMatrix[13] * mMatrix[4] - mMatrix[5] * mMatrix[12]) / det,
                                    -(mMatrix[15] * mMatrix[1] - mMatrix[3] * mMatrix[13]) / det,
                                     (mMatrix[15] * mMatrix[0] - mMatrix[3] * mMatrix[12]) / det,
                                    -(mMatrix[13] * mMatrix[0] - mMatrix[1] * mMatrix[12]) / det,
                                     (mMatrix[7]  * mMatrix[1] - mMatrix[3] * mMatrix[5])  / det,
                                    -(mMatrix[7]  * mMatrix[0] - mMatrix[3] * mMatrix[4])  / det,
                                     (mMatrix[5]  * mMatrix[0] - mMatrix[1] * mMatrix[4])  / det);
        }else{
            return Identity;
        }
        
    }
    
    public Vector2f transformPoint(float x, float y) {
        return new Vector2f(mMatrix[0] * x + mMatrix[4] * y + mMatrix[12],
                            mMatrix[1] * x + mMatrix[5] * y + mMatrix[13]);
    }
    
    public Vector2f transformPoint(Vector2f point) {
        return transformPoint(point.x, point.y);
    }
    
    public BoundingBox transformRect(BoundingBox rect) {
    
        Vector2f points[] = new Vector2f[] {
            transformPoint(rect.x, rect.y),
            transformPoint(rect.x, rect.y + rect.height),
            transformPoint(rect.x + rect.width, rect.y),
            transformPoint(rect.x + rect.width, rect.y + rect.height)
        };
        
        float left      = points[0].x;
        float top       = points[0].y;
        float right     = points[0].x;
        float bottom    = points[0].y;
        for (int i = 1; i < 4; ++i)
        {
            if      (points[i].x < left)   left     = points[i].x;
            else if (points[i].x > right)  right    = points[i].x;
            if      (points[i].y < top)    top      = points[i].y;
            else if (points[i].y > bottom) bottom   = points[i].y;
        }

        return new BoundingBox(left, top, right - left, bottom - top);
        
    }
    
    public Transform combine(Transform transform) {
        
        float[] a = mMatrix;
        float[] b = transform.getMatrix();
        
        this.set(   a[0] * b[0]  + a[4] * b[1]  + a[12] * b[3],
                    a[0] * b[4]  + a[4] * b[5]  + a[12] * b[7],
                    a[0] * b[12] + a[4] * b[13] + a[12] * b[15],
                    a[1] * b[0]  + a[5] * b[1]  + a[13] * b[3],
                    a[1] * b[4]  + a[5] * b[5]  + a[13] * b[7],
                    a[1] * b[12] + a[5] * b[13] + a[13] * b[15],
                    a[3] * b[0]  + a[7] * b[1]  + a[15] * b[3],
                    a[3] * b[4]  + a[7] * b[5]  + a[15] * b[7],
                    a[3] * b[12] + a[7] * b[13] + a[15] * b[15]);
        
        return this;
        
    }
    
    public Transform translate(float x, float y) {
        Transform translation = new Transform(  1, 0, x,
                                                0, 1, y,
                                                0, 0, 1);
        return combine(translation);
    }
    
    public Transform translate(Vector2f point) {
        return translate(point.x, point.y);
    }
    
    public Transform rotate(float angle) {
        
        float rad = (float)Math.toRadians(angle);
        float cos = (float)Math.cos(rad);
        float sin = (float)Math.sin(rad);
        
        Transform rotation = new Transform( cos,    -sin,   0,
                                            sin,    cos,    0,
                                            0,      0,      0);
        
        return combine(rotation);
        
    }
    
    public Transform rotate(float angle, float centerX, float centerY) {
        
        float rad = (float)Math.toRadians(angle);
        float cos = (float)Math.cos(rad);
        float sin = (float)Math.sin(rad);
        
        Transform rotation = new Transform( cos,    -sin,   centerX * (1 - cos) + centerY * sin,
                                            sin,    cos,    centerY * (1 - cos) + centerX * sin,
                                            0,      0,      0);
        
        return combine(rotation);
        
    }
    
    public Transform rotate(float angle, Vector2f center) {
        return rotate(angle, center.x, center.y);
    }
    
    public Transform scale(float scaleX, float scaleY) {

        Transform scaling = new Transform(  scaleX, 0,      0,
                                            0,      scaleY, 0,
                                            0,      0,      1);
        
        
        return combine(scaling);
        
    }
    
    public Transform scale(float scaleX, float scaleY, float centerX, float centerY) {
        
        Transform scaling = new Transform(  scaleX, 0,      centerX * (1 - scaleX),
                                            0,      scaleY, centerY * (1 - scaleY),
                                            0,      0,      1);
        
        return combine(scaling);
        
    }
    
    public Transform scale(Vector2f factors) {
        return scale(factors.x, factors.y);
    }
    
    public Transform scale(Vector2f factors, Vector2f center) {
        return scale(factors.x, factors.y, center.x, center.y);
    }
    
    // Operator *=
    public Transform multiply(Transform other) {
        return this.combine(other);
    }
    
    // operator *
    public static Transform multiply(Transform left, Transform right) {
        return new Transform(left).combine(right);
    }
    
    // operator * Vector2f
    public static Vector2f multiply(Transform left, Vector2f right) {
        return left.transformPoint(right);
    }
    
}
