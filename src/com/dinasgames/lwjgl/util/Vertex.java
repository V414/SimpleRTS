/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.math.Vector2f;

/**
 *
 * @author Jack
 */
public class Vertex {
    
    public float tx, ty;
    public float x,y;
    public Color color;
    
    public Vertex() {
        this.x = this.y = 0;
        this.tx = this.ty = 0;
        this.color = Color.WHITE;
    }
    
    public Vertex(Vector2f position) {
        this();
        this.x = position.x;
        this.y = position.y;
    }
    
    public Vertex(Vector2f position, Color color) {
        this(position);
        this.color = color;
    }
    
    public Vertex(Vector2f position, Vector2f texCoords ) {
        this(position);
        this.tx = texCoords.x;
        this.ty = texCoords.y;
    }
    
    public Vertex(Vector2f position, Color color, Vector2f texCoords) {
        this(position, texCoords);
        this.color = color;
    }
    
    public Vertex(Vertex other) {
        tx = other.tx;
        ty = other.ty;
        x = other.x;
        y = other.y;
        color = new Color(other.color);
    }
    
}
