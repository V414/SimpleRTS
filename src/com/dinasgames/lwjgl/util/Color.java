/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.Math.RandomNumber;

/**
 *
 * @author Jack
 */
public class Color {
    
    protected int r,g,b,a;
    
    public static final Color WHITE = new Color( 255, 255, 255 );
    public static final Color BLACK = new Color( 0, 0, 0 );
    public static final Color RED = new Color( 255, 0, 0 );
    public static final Color GREEN = new Color( 0, 255, 0 );
    public static final Color BLUE = new Color( 0, 0, 255 );
    public static final Color YELLOW = new Color( 255, 255, 0 );
    public static final Color PINK = new Color( 255,192,203 );
    public static final Color CYAN = new Color( 0,255,255 );
    public static final Color ORANGE = new Color( 255,165,0 );
    public static final Color MEGENTA = new Color( 255,0,255 );
    
    public Color() {
        r = g = b = a = 0;
    }
    
    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 255;
    }
    
    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public Color(Color other) {
        this(other.getRed(), other.getGreen(), other.getBlue(), other.getAlpha());
    }
    
    public int getRed() {
        return this.r;
    }
    
    public int getGreen() {
        return this.g;
    }
    
    public int getBlue() {
        return this.b;
    }
    
    public int getAlpha() {
        return this.a;
    }
    
    public GLColor toGLColor() {
        return new GLColor( ((float)this.r) / 255.f, ((float)this.g) / 255.f, ((float)this.b) / 255.f, ((float)this.a) / 255.f );
    }
    
    public int toInt() {
        byte b1, b2, b3, b4;
        b1 = (byte)r;
        b2 = (byte)g;
        b3 = (byte)b;
        b4 = (byte)a;
        return (int)(((0xFF & b1) << 24) | ((0xFF & b2) << 16) | ((0xFF & b3) << 8) | (0xFF & b4));
    }
    
    public static Color getRandom() {  
        return new Color( RandomNumber.between(0, 255), RandomNumber.between(0, 255), RandomNumber.between(0, 255), RandomNumber.between(0, 255) );
    }
    
    public boolean equals(Color other) {
        return (other.getRed() == r && other.getGreen() == g && other.getBlue() == b && other.getAlpha() == a);
    }
    
}
