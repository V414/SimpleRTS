package com.dinasgames.main.Games;

import com.dinasgames.main.System.Clock;

/**
 * A class used to handle game logic and render a window.
 * @author Jack
 */
public class Game {
    
    protected Clock mStartClock;
    protected Clock mClock;
    protected boolean mRunning;
    
    public static Game current;
    
    protected Game() {
        mStartClock = null;
        mClock = null;
        mRunning = false;
    }
    
    protected void tick() {
        
    }
    
    protected void load() {
        
        mStartClock = new Clock();
        mClock = new Clock();
        mRunning = false;
        
    }
    
    protected void unload() {
        
    }
    
    public Clock getStartClock() {
        return mStartClock;
    }
    
    public Clock getClock() {
        return mClock;
    }
    
    public boolean isRunning() {
        return mRunning;
    }
    
    public void stop() {
        mRunning = false;
    }
    
    public void run() {
        
        load();
        
        mRunning = true;
        
        while(mRunning) {
            tick();
        }
        
        unload();
        
    }
    
}
