/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Games;

import com.dinasgames.main.Scenes.TestScene;

/**
 *
 * @author Jack
 */
public class TestGame extends SimpleGame {
    
    @Override
    protected void load() {
        
        super.load();
        
        // Goto the test scene
        setScene(new TestScene());
        
    }
    
}
