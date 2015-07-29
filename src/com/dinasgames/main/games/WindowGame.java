/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.games;

import com.dinasgames.engine.graphics.RenderWindow;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.engine.system.Time;
import com.dinasgames.engine.system.Timer;

/**
 *
 * @author Jack
 */
public class WindowGame extends Game {
    
    protected Timer mDrawCountTimer;
    protected RenderWindow mWindow;
    
    protected WindowGame() {
        
        mWindow = null;
        mDrawCountTimer = null;
        
    }
    
    @Override
    public void load() {
        
        super.load();
        
        // Open the window
        mWindow = new RenderWindow();
        mWindow.setTitle("Simple RTS");
        mWindow.setSize(1280, 720);
        mWindow.center();

        mDrawCountTimer = Timer.every(Time.seconds(1.f), () -> {
            
            System.out.println("Draw Count: " + mWindow.getRenderer().getDrawCount());
            
        });
        
    }
    
    @Override
    public void tick() {
        
        super.tick();
        
        mWindow.update();
        mWindow.display();
        
        // Stop the game when the window is closed
        if(mWindow.shouldClose()) {
            stop();
        }
        
    }
    
    @Override
    public void unload() {
        
        super.unload();
        
        mDrawCountTimer.stop();
        
        // Close the game window
        if(mWindow.isVisible()) {
            mWindow.close();
        }
        
    }
    
    public RenderWindow getWindow() {
        return mWindow;
    }
    
    public Renderer getRenderer() {
        return mWindow.getRenderer();
    }
    
}
