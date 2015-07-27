//Our Main Method - should be quite empty for now

package com.dinasgames.main;

import com.dinasgames.lwjgl.util.LWJGL;
import com.dinasgames.main.games.TestGame;

public class Main {
    
    public static void main(String args[]) {
        
        try {
            
            LWJGL.init();
            
            // Run Test Game
            TestGame test = new TestGame();
            test.run();
            
        } catch(RuntimeException e) {
            e.printStackTrace();
        } finally {
            LWJGL.free();
        }
        
    };
    
}
