/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Games;

import com.dinasgames.server.Main;
import com.dinasgames.server.ServerGame;
import com.dinasgames.server.net.Buffer;
import com.dinasgames.server.net.NonBlockingClient;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack
 */
public class ClientGame extends LocalGame {
    
    protected ServerGame mServerGame;
    protected NonBlockingClient mClient;
    
    public ClientGame() {
        
        mServerGame = null;
        mClient = null;
        
    }
    
    @Override
    public void load() {
        
        super.load();
        
        // Start the server
        mServerGame = new ServerGame();
        mServerGame.load();
        
        try {
            // Start the client
            mClient = new NonBlockingClient(new NonBlockingClient.Listener() {
                
                @Override
                public void socketConnected(NonBlockingClient client) {
                    try {
                        System.out.println("Client connected to " + client.getRemoteAddress() + ".");
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                @Override
                public void socketDisconnected(NonBlockingClient client) {
                    System.out.println("Client disconnected from server.");
                }
                
                @Override
                public void socketMessage(NonBlockingClient client, Buffer buffer) {
                    
                    //                    System.out.println("Client socket message!");
                    //                    System.out.println("Header: " + buffer.readShort());
                    //                    System.out.println("Content: " + buffer.readString());
                    
                    try {
                        client.send(new Buffer().writeShort((short)1).writeString("Hello Server!"));
                    } catch (ClosedChannelException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                @Override
                public void socketConnecting(NonBlockingClient client) {
                    System.out.println("Client is connecting to server...");
                }
                
                @Override
                public void socketDisconnecting(NonBlockingClient client) {
                    System.out.println("Client is disconnecting from server...");
                }
            })
                    .connect("127.0.0.1", 12000)
                    .send(new Buffer().writeShort((short)1).writeString("Hello Server!"));
        } catch (ClosedChannelException ex) {
            Logger.getLogger(ClientGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void tick() {
        
        // Update local game logic
        super.tick();
        
        // Update server logic and socket
        mServerGame.tick();
        
        // Update client socket
        mClient.update();
        
    }
    
    @Override
    public void unload() {
        
        super.unload();
        mServerGame.unload();
        mClient.disconnect();
        
    }
        
    
}
