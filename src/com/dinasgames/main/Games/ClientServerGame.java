/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Games;

import com.dinasgames.main.System.Time;
import com.dinasgames.main.System.Timer;
import com.dinasgames.server.Main;
import com.dinasgames.server.ServerGame;
import com.dinasgames.server.net.Buffer;
import com.dinasgames.server.net.NonBlockingClient;
import com.dinasgames.server.net.packets.PacketKeepAlive;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack
 */
public class ClientServerGame extends LocalGame {
    
    protected ServerGame mServerGame;
    protected NonBlockingClient mClient;
    protected Timer mPingTimer;
    
    public ClientServerGame() {
        
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
                    .register(new PacketKeepAlive())
                    .connect("127.0.0.1", 12000);
        } catch (Exception ex) {
            Logger.getLogger(ClientServerGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Send ping to the server
        mPingTimer = Timer.every(Time.seconds(1.f), () -> {
            
            if(mClient.isConnected()) {
                try {
                    mClient.send(new PacketKeepAlive());
                } catch (ClosedChannelException ex) {
                    Logger.getLogger(ClientServerGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            System.out.println("Client ping: " + mClient.getPing().asMilliseconds() + "ms");
            
        });
        
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
        mPingTimer.stop();
        
    }
        
    
}
