/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Games;

import com.dinasgames.main.Graphics.Renderer;
import com.dinasgames.main.System.Window;

/**
 *
 * @author Jack
 */
public class WindowGame extends Game {
    
    protected Window mWindow;
    
    protected WindowGame() {
        
        mWindow = null;
        
    }
    
    @Override
    public void load() {
        
        super.load();
        
        // Open the window
        mWindow = new Window( "Simple RTS", 1280, 720 );
        
    }
    
    @Override
    public void tick() {
        
        super.tick();
        
    }
    
    @Override
    public void unload() {
        
        super.unload();
        
        // Close the game window
        if(mWindow.isOpen()) {
            mWindow.close();
        }
        
    }
    
    public Window getWindow() {
        return mWindow;
    }
    
    public Renderer getRenderer() {
        return mWindow.getRenderer();
    }
    
}
