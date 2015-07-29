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
public interface RenderTextureImpl {
    
    public void destruct();
    public boolean create(int width, int height, int textureId, boolean depthBuffer);    
    public boolean activate(boolean active);
    public void updateTexture(int textureId);
    
}
