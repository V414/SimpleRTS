/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects;

import com.dinasgames.lwjgl.util.Renderer;

/**
 *
 * @author Jack
 */
public interface RenderEvents {
    
    public void onRenderAdd( Renderer renderer );       // << Called when the object is added to a renderer.
    public void onRenderRemove( Renderer renderer );    // << Called when the object is removed from a renderer.
    public void onRenderUpdate( Renderer renderer );    // << Called when the renderer requests an update from the object
    
}
