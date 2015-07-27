/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.games;

import com.dinasgames.server.ServerGame;

/**
 *
 * @author Jack
 */
public class ClientServerGame extends ClientGame {
    
    protected ServerGame mServerGame;
    
    public ClientServerGame() {
        
        mServerGame = null;
        
    }
    
    @Override
    public void load() {
        
        super.load();
        
        // Start the server
        mServerGame = new ServerGame();
        mServerGame.load();
        
    }
    
    @Override
    public void tick() {
        
        // Update local game logic
        super.tick();
        
        // Update server logic and socket
        mServerGame.tick();
        
    }
    
    @Override
    public void unload() {
        
        super.unload();
        mServerGame.unload();
        
    }
        
    
}
