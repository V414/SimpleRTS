package com.dinasgames.main.games;

import com.dinasgames.lwjgl.util.LWJGL;
import com.dinasgames.main.system.Clock;
import com.dinasgames.main.system.Timer;

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
    
    public void tick() {
        
    }
    
    public void load() {
        
        
        
        mStartClock = new Clock();
        mClock = new Clock();
        mRunning = false;
        
    }
    
    public void unload() {
        
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
            
            // Update game logic
            tick();
            
            // Run timers
            Timer.update();
            
        }
        
        unload();
        
    }
    
}
