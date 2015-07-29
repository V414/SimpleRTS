/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.net;

import com.dinasgames.engine.graphics.Renderer;

/**
 *
 * @author Jack
 */
public interface RenderObject {
    
    public void onRendererChange(Renderer renderer);    // << Called when a new Renderer object is assigned to this object
    public void onRenderUpdate(Renderer renderer);      // << Called every frame update
    
}
