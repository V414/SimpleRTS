/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main;

import com.dinasgames.main.System.Clock;
import com.dinasgames.main.System.Window;
import com.dinasgames.main.System.Mouse;
import com.dinasgames.main.Scenes.Scene;

/**
 * A class used to handle game logic and render a window.
 * @author Jack
 */
public class Game {
    
    protected boolean mRunning;
    protected Window mWindow;
    protected Scene mScene;
    
    public static Game current;
        
    // Frame indepentant
    protected double mAccumulator, mCurrentTime, mT, mDT;
    protected int mFps, mFpsCounter;
    protected Clock mFpsClock;
    
    /**
     * Construct the Game class.
     */
    public Game() {
        
        // Initialize variables
        mRunning        = true;
        
        mFpsClock       = new Clock();
        
        mAccumulator    = 0.0;
        mCurrentTime    = 0.0;
        mT              = 0.0;
        mDT             = 0.01;
        mFps            = 0;
        mFpsCounter     = 0;
        
        mWindow = new Window( "Simple RTS", 1280, 720 );
        mScene = null;
        
    }
    
    /**
     * Handle a rendering and logic tick.
     */
    public void tick() {
        
        // Update mouse
        Mouse.tick();
        
        // Update game logic
        double newTime = System.currentTimeMillis() / 1000.0;
        double frameTime = newTime - mCurrentTime;
        
        // Stop the game going into an infinite loop of DOOOM!
        if(frameTime > .25) {
            frameTime = .25;
        }
        
        mCurrentTime = newTime;
        mAccumulator += frameTime;
        
        // Execute steps until we have caught up
        while(mAccumulator >= mDT) {
            
            // Game logic happens in this function
            singleStep();
            
            mAccumulator -= mDT;
            mT += mDT;
            
        }
        
        // Render the game window
        mWindow.render();
        
        // Calculate FPS
        mFpsCounter++;
        if(mFpsClock.getElapsedTime().asSeconds() >= 1.f) {
            
            mFps = mFpsCounter;
            mFpsCounter = 0;
            
            System.out.println("FPS: " + Integer.toString(mFps));
            
            mFpsClock.restart();
            
        }
        
    }
    
    /**
     * Check whether the game is still running.
     * @return boolean
     */
    public boolean isRunning() {
        return mRunning;
    }
    
    /**
     * Stop the running game. Note that the game won't be stopped until the end of the current tick.
     */
    public void stop() {
        mRunning = false;
    }
    
    /**
     * Does a single game step (logic step).
     */
    protected void singleStep() {
        
        // Tick the scene logic
        if(mScene != null) {
            mScene.tick(mCurrentTime);
        }
        
    }
    
    public Window getWindow() {
        return mWindow;
    }
    
    public Scene getScene() {
        return mScene;
    }
    
    public void setScene(Scene scene) {
        mScene = scene;
        mScene.onCreate();
    }
    
}
