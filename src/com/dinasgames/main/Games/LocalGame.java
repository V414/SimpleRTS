package com.dinasgames.main.Games;

import com.dinasgames.main.Graphics.Renderer;
import com.dinasgames.main.Players.LocalPlayer;
import com.dinasgames.main.Players.PlayerList;
import com.dinasgames.main.Scenes.Scene;
import com.dinasgames.main.System.Clock;
import com.dinasgames.main.System.Mouse;
import com.dinasgames.main.System.Time;
import com.dinasgames.main.System.Timer;
import java.awt.Color;

/**
 * The template for all games underneath
 * @author Jack
 */
public class LocalGame extends WindowGame {
    
    protected Scene mScene;
        
    // Frame indepentant
    protected LocalPlayer mPlayer;
    protected double mAccumulator, mCurrentTime, mT, mDT;
    protected int mFps, mFpsCounter;
    //protected Clock mFpsClock;
    protected Timer mFpsTimer;
    protected PlayerList mPlayerList;
    
    /**
     * Construct the Game class.
     */
    public LocalGame() {
        
        //mFpsClock       = null;
        
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
    public void load() {
        
        super.load();
        
        // Initialize variables
        mRunning        = true;
        
        //mFpsClock       = new Clock();
        
        mAccumulator    = 0.0;
        mCurrentTime    = 0.0;
        mT              = 0.0;
        mDT             = 0.01;
        mFps            = 0;
        mFpsCounter     = 0;
        
        mScene = null;
        
        mPlayerList = new PlayerList();
        mPlayerList.setRenderer(getWindow().getRenderer());
        mPlayerList.setScene(mScene);
        
        // Create a local player
        mPlayer = null;//new LocalPlayer();
        
        //mPlayerList.add(mPlayer);
        
        // Set the background color
        getWindow().getRenderer().setBackgroundColor(new Color(128,128,128,255));
        
        // Keep track of fps
        mFpsTimer = Timer.every(Time.seconds(1.f), () -> {
            
            mFps = mFpsCounter;
            mFpsCounter = 0;
            
            System.out.println("FPS: " + mFps);
            
        });
        
    }
    
    @Override
    public void unload() {
        
        super.unload();
        
        mFpsTimer.stop();
        
    }
    
    /**
     * Handle a rendering and logic tick.
     */
    @Override
    public void tick() {
        
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
        mScene.setGame(this);
        mScene.setRenderer(mWindow.getRenderer());
        mScene.onCreate();
        
        // The mouse should use this scene
        Mouse.setScene(scene);
        mPlayerList.setScene(mScene);
        
    }
    
    public PlayerList getPlayerList() {
        return mPlayerList;
    }
    
    public LocalPlayer getPlayer() {
        return mPlayer;
    }
    
}
