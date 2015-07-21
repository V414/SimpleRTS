package com.dinasgames.main.Games;

import com.dinasgames.main.Graphics.Renderer;
import com.dinasgames.main.Players.LocalPlayer;
import com.dinasgames.main.Players.PlayerList;
import com.dinasgames.main.Scenes.Scene;
import com.dinasgames.main.System.Clock;
import com.dinasgames.main.System.Mouse;
import java.awt.Color;

/**
 * The template for all games underneath
 * @author Jack
 */
public class SimpleGame extends WindowGame {
    
    protected Scene mScene;
        
    // Frame indepentant
    protected LocalPlayer mPlayer;
    protected double mAccumulator, mCurrentTime, mT, mDT;
    protected int mFps, mFpsCounter;
    protected Clock mFpsClock;
    protected PlayerList mPlayerList;
    
    /**
     * Construct the Game class.
     */
    public SimpleGame() {
        
        mFpsClock       = null;
        
        mAccumulator    = 0.0;
        mCurrentTime    = 0.0;
        mT              = 0.0;
        mDT             = 0.01;
        mFps            = 0;
        mFpsCounter     = 0;
        
        mScene = null;
        
        mPlayerList = null;
        
    }
    
    @Override
    protected void load() {
        
        super.load();
        
        // Initialize variables
        mRunning        = true;
        
        mFpsClock       = new Clock();
        
        mAccumulator    = 0.0;
        mCurrentTime    = 0.0;
        mT              = 0.0;
        mDT             = 0.01;
        mFps            = 0;
        mFpsCounter     = 0;
        
        mScene = null;
        
        mPlayerList = new PlayerList();
        
        // Create a local player
        mPlayer = LocalPlayer.create();
        
        // Set the background color
        Renderer.getCurrent().setBackgroundColor(new Color(128,128,128,255));
        
    }
    
    @Override
    protected void unload() {
        
        super.unload();
        
    }
    
    /**
     * Handle a rendering and logic tick.
     */
    @Override
    protected void tick() {
        
        super.tick();
        
        // Update mouse
        Mouse.tick();
        
        // Update players
        mPlayerList.update();
        
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
     * Does a single game step (logic step).
     */
    protected void singleStep() {
        
        // Tick the scene logic
        if(mScene != null) {
            mScene.tick(mCurrentTime);
        }
        
    }
    
    public Scene getScene() {
        return mScene;
    }
    
    public void setScene(Scene scene) {
        mScene = scene;
        mScene.onCreate();
    }
    
    public PlayerList getPlayerList() {
        return mPlayerList;
    }
    
    public LocalPlayer getPlayer() {
        return mPlayer;
    }
    
}
