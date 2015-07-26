/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

/**
 *
 * @author Jack
 */
public class GLColor {
    
    protected float r,g,b,a;
    
    public static final GLColor WHITE = new GLColor( 1.f, 1.f, 1.f );
    public static final GLColor BLACK = new GLColor( 0.f, 0.f, 0.f );
    public static final GLColor RED = new GLColor( 1.f, 0.f, 0.f );
    public static final GLColor GREEN = new GLColor( 0.f, 1.f, 0.f );
    public static final GLColor BLUE = new GLColor( 0.f, 0.f, 1.f );
    
    public GLColor() {
        r = g = b = a = 0.f;
    }
    
    public GLColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.f;
    }
    
    public GLColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public float getRed() {
        return this.r;
    }
    
    public float getGreen() {
        return this.g;
    }
    
    public float getBlue() {
        return this.b;
    }
    
    public float getAlpha() {
        return this.a;
    }
    
    public Color toColor() {
        return new Color( (byte)(this.r * 255.f), (byte)(this.g * 255.f), (byte)(this.b * 255.f), (byte)(this.a * 255.f) );
    }
    
    public int toInt() {
        byte b1, b2, b3, b4;
        b1 = (byte)r;
        b2 = (byte)g;
        b3 = (byte)b;
        b4 = (byte)a;
        return (int)(((0xFF & b1) << 24) | ((0xFF & b2) << 16) | ((0xFF & b3) << 8) | (0xFF & b4));
    }
    
}
