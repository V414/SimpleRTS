package com.dinasgames.main.Games;

import com.dinasgames.main.Scenes.TestScene;

/**
 * An pretty empty test game
 * @author Jack
 */
public class TestGame extends ClientServerGame {
    
    @Override
    public void load() {
        
        super.load();
        
        // Goto the test scene
        setScene(new TestScene(this));
        
    }
    
}
