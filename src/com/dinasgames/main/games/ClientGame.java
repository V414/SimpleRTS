/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.games;

import com.dinasgames.engine.math.RandomNumber;
import com.dinasgames.engine.system.Time;
import com.dinasgames.engine.system.Timer;
import com.dinasgames.main.Version;
import com.dinasgames.server.Main;
import com.dinasgames.engine.network.NonBlockingClient;
import com.dinasgames.engine.network.Packet;
import com.dinasgames.server.net.packets.*;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack
 */
public class ClientGame extends LocalGame {
    
    protected String mName;
    protected NonBlockingClient mClient;
    protected Timer mPingTimer;
    protected int mPing;
    
    public ClientGame() {
        
        mClient = null;
        mName = "Client" + RandomNumber.between(1000, 9999);
        
    }
    
    @Override
    public void load() {
        
        super.load();
        
        try {
            // Start the client
            mClient = new NonBlockingClient(new NonBlockingClient.Listener() {
                
                @Override
                public void socketConnected(NonBlockingClient client) {
                    try {
                        System.out.println("Client connected to " + client.getRemoteAddress() + ". Logging in...");
                        client.send(new PacketLogin10(mName, Version.current));
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                @Override
                public void socketDisconnected(NonBlockingClient client) {
                    System.out.println("Client disconnected from server.");
                }
                
                @Override
                public void socketConnecting(NonBlockingClient client) {
                    System.out.println("Client is connecting to server...");
                }
                
                @Override
                public void socketDisconnecting(NonBlockingClient client) {
                    System.out.println("Client is disconnecting from server...");
                }

                @Override
                public void clientPacket(NonBlockingClient client, Packet packet) {
                    
                    switch(packet.getID()) {
                        
                        default:
                            // Ignore
                            break;
                        
                    }
                    
                }

                @Override
                public void clientLoginSuccessful(NonBlockingClient client, String serverName, Version serverVersion) {
                    
                    System.out.println("Client logged into server successfully! Server: " + serverName + " " + serverVersion);
                    
                }

                @Override
                public void clientLoginFailure(NonBlockingClient client, String reason) {
                    
                    System.out.println("Client failed to login to server. Reason: " + reason);
                    
                }
            })
                    .register(new PacketKeepAlive244())
                    .register(new PacketLogin10())
                    .register(new PacketLoginFailed11())
                    .connect("127.0.0.1", 12000);
        } catch (Exception ex) {
            Logger.getLogger(ClientServerGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Send ping to the server
        mPingTimer = Timer.every(Time.seconds(1.f), () -> {
            
            if(mClient.isConnected()) {
                try {
                    mClient.send(new PacketKeepAlive244());
                } catch (Exception ex) {
                    Logger.getLogger(ClientServerGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            //System.out.println("Client ping: " + mClient.getPing().asMilliseconds() + "ms");
            mPing = (int)mClient.getPing().asMilliseconds();
            
        });
        
    }
    
    @Override
    public void tick() {
        
        // Update local game logic
        super.tick();
        
        try {
            // Update server logic and socket
            
            // Update client socket
            mClient.update();
        } catch (Exception ex) {
            Logger.getLogger(ClientGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void unload() {
        
        super.unload();
        mClient.disconnect();
        mPingTimer.stop();
        
    }
        
    
}
