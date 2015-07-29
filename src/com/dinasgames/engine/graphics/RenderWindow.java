/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.graphics;

import com.dinasgames.engine.window.Window;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Jack
 */
public class RenderWindow extends Window {
    
    protected Color mBackgroundColor;
    protected Renderer mRenderer;
    
    public RenderWindow() {
        mRenderer = new Renderer();
        mBackgroundColor = Color.WHITE;
    }
    
    public Renderer getRenderer() {
        return mRenderer;
    }
    
    public RenderWindow setBackgroundColor(Color color) {
        mBackgroundColor = color;
        return this;
    }
    
    public Color getBackgroundColor() {
        return mBackgroundColor;
    }
    
    @Override
    /**
     * Clear the current window, render using the Renderer object and display it in the window.
     */
    public RenderWindow display() {
        
        // Clear display
        clear(mBackgroundColor);
        
        // Render objects
        mRenderer.render(this);
        
        // Update display
        super.display();
        
        return this;
    }
    
    /**
     * Capture an Image of the current display.
     * @return 
     */
    public Image capture() {
        
        Image image = new Image();
        
        if(setActive(true)) {
            
            int width = getWidth();
            int height = getHeight();
            
            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
            ByteBuffer row = BufferUtils.createByteBuffer(width * 4);
            for(int i = 0; i < height; ++i) {
                row.clear();
                GL11.glReadPixels(0, height - i - 1, width, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, row);
                for(int j = 0; j < width * 4; j++) {
                    pixels.put(row.get());
                }
            }
            
            pixels.flip();
            
            image.create(width, height, pixels);
            
        }
        
        return image;
        
    }
    
}
