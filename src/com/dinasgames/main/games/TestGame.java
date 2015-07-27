package com.dinasgames.main.games;

import com.dinasgames.main.players.LocalPlayer;
import com.dinasgames.main.scenes.TestScene;

/**
 * An pretty empty test game
 * @author Jack
 */
public class TestGame extends ClientServerGame {
    
    @Override
    public void load() {
        
        super.load();
        
        mPlayer = new LocalPlayer();
        mPlayerList.add(mPlayer);
        
        // Goto the test scene
        setScene(new TestScene(this));
        
    }
    
}
