/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Games;

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
    protected void load() {
        
        super.load();
        
        // Open the window
        mWindow = new Window( "Simple RTS", 1280, 720 );
        
    }
    
    @Override
    protected void tick() {
        
        super.tick();
        
    }
    
    @Override
    protected void unload() {
        
        super.unload();
        
        // Close the game window
        if(mWindow.isOpen()) {
            mWindow.close();
        }
        
    }
    
    public Window getWindow() {
        return mWindow;
    }
    
}
