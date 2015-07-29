/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.graphics;

/**
 *
 * @author Jack
 */
public class RenderStates {
    
    public static final RenderStates Default = new RenderStates(new BlendMode(
    BlendMode.Factor.SrcAlpha, BlendMode.Factor.OneMinusSrcAlpha, BlendMode.Equation.Add,
    BlendMode.Factor.One, BlendMode.Factor.OneMinusSrcAlpha, BlendMode.Equation.Add));
    
    public BlendMode    blendMode;
    public Transform    transform;
    public Texture      texture;
    public boolean      gui;
    //public Shader       shader;
    
    ////////////////////////////////////////////////////////////
    public RenderStates() {
        this.blendMode  = BlendMode.BlendAlpha;
        this.transform  = new Transform();
        this.texture    = null;
        this.gui        = false;
    }
  
    public RenderStates(Transform transform) {
        this.blendMode = BlendMode.BlendAlpha;
        this.transform = transform;
    }

    public RenderStates(BlendMode blendMode) {
        this.blendMode = blendMode;
        this.transform = new Transform();
    }

    public RenderStates(BlendMode blendMode, Transform transform) {
        this.blendMode = blendMode;
        this.transform = transform;
    }
    
    public RenderStates(RenderStates other) {
        this(other.blendMode, new Transform(other.transform));
    }
    
    public static RenderStates getDefault() {
        return new RenderStates(Default);
    }

}
