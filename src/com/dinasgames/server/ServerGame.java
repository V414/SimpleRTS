///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package com.dinasgames.server;

import com.dinasgames.main.games.Game;
import com.dinasgames.main.networking.Network;
import com.dinasgames.main.players.PlayerList;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.main.system.Clock;
import com.dinasgames.main.Version;
import com.dinasgames.server.net.NonBlockingServer;
import com.dinasgames.server.net.Packet;
import com.dinasgames.server.net.packets.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack
 */
public class ServerGame extends Game {
    
    private NonBlockingServer mServer;
    private Scene mScene;
        
    // Frame indepentant
    private double mAccumulator, mCurrentTime, mT, mDT;
    private int mFps, mFpsCounter;
    private Clock mFpsClock;
    private PlayerList mPlayerList;
    private Clock mTimePassedClock;
    
    /**
     * Construct the Game class.
     */
    public ServerGame() {
        
        mFpsClock       = null;
        
        mAccumulator    = 0.0;
        mCurrentTime    = 0.0;
        mT              = 0.0;
        mDT             = 0.01;
        mFps            = 0;
        mFpsCounter     = 0;
        
        mScene = null;
        
        mPlayerList = null;
        mServer = null;
        
    }
    
    @Override
    public void load() {
        
        super.load();
        
        Network._client = false;
        
        // Initialize variables        
        mFpsClock       = new Clock();
        
        mTimePassedClock = new Clock();
        
        mAccumulator    = 0.0;
        mCurrentTime    = 0.0;
        mT              = 0.0;
        mDT             = 0.01;
        mFps            = 0;
        mFpsCounter     = 0;
        
        mScene = null;
        
        mPlayerList = new PlayerList();
        
        mServer = new NonBlockingServer(new NonBlockingServer.Listener() {

                @Override
                public void serverStarted() {
                    System.out.println("Server started!");
                }

                @Override
                public void serverStopped() {
                    System.out.println("Server stopped.");
                }

                @Override
                public void socketConnected(NonBlockingServer.Socket socket) {
                    System.out.println("Server socket " + socket.getId() + " connected!");
                }

                @Override
                public void socketDisconnected(NonBlockingServer.Socket socket) {
                    System.out.println("Server socket " + socket.getId() + " disconnected!");
                }

                @Override
                public void serverStarting() {
                    System.out.println("Starting server...");
                }

                @Override
                public void serverStopping() {
                    System.out.println("Stopping server...");
                }

                @Override
                public void clientPacket(NonBlockingServer.Socket socket, Packet packet) {
                    
                    switch(packet.getID()) {
                        
                        default: 
                            // Ignore other IDs
                            break;
                            
                    }
                    
                }

                @Override
                public void clientLoginSuccess(NonBlockingServer.Socket socket, String name, Version version) {
                    
                    System.out.println("Client " + socket.getId() + " logged in with name '" + name + "' and version " + version);
                    
                }

                @Override
                public void clientLoginFailure(NonBlockingServer.Socket socket, String reason) {
                    
                    System.out.println("Client " + socket.getId() + " failed to login. Reason: " + reason);
                    
                }


            })
                .register(new PacketKeepAlive244())
                .register(new PacketLogin10())
                .listen(12000);
        
        //setServerScene(new TestScene().setGame(this));
        
        // Set the background color#
        // Server doesn't render!
        //Renderer.getCurrent().setBackgroundColor(new Color(128,128,128,255));
        
    }
    
    @Override
    public void unload() {
        
        super.unload();
        
        Network._client = false;
        
        if(mServer.isRunning()) {
            mServer.close();
        }
        
    }
    
    /**
     * Handle a rendering and logic tick.
     */
    @Override
    public void tick() {
        
        super.tick();

        // Toggle the network to server mode for this tick
        Network._client = false;
        
        try {
            mServer.update();
        } catch (Exception ex) {
            Logger.getLogger(ServerGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Update mouse
        //Mouse.tick();
        
        // Update players
        mPlayerList.update();
        
        // Update game logic
        double newTime = System.currentTimeMillis() / 1000.0;
        double frameTime = newTime - mCurrentTime;
        
        // Stop the game going into an infinite loop of DOOOM!
        if(frameTime > .25) {
            frameTime = .25;
        }
        
        mCurrentTime = newTime;
        mAccumulator += frameTime;
        
        // Execute steps until we have caught up
        while(mAccumulator >= mDT) {
            
            // Game logic happens in this function
            singleStep();
            
            mAccumulator -= mDT;
            mT += mDT;
            
        }
        
        // Render the game window
        //mWindow.render();
        
        // Calculate FPS
        mFpsCounter++;
        if(mFpsClock.getElapsedTime().asSeconds() >= 1.f) {
            
            mFps = mFpsCounter;
            mFpsCounter = 0;
            
            System.out.println("Server FPS: " + Integer.toString(mFps));
            
            mFpsClock.restart();
            
        }
        
    }
    
    /**
     * Does a single game step (logic step).
     */
    private void singleStep() {
        
        // Tick the scene logic
        if(mScene != null) {
            mScene.tick(mTimePassedClock.getElapsedTime());
            mTimePassedClock.restart();
        }
        
    }
    
    public Scene getScene() {
        return mScene;
    }
    
    public void setScene(Scene scene) {
        Network._client = false;
        mScene = scene;
        mScene.onCreate();
    }
    
    public PlayerList getPlayerList() {
        return mPlayerList;
    }
    
    public NonBlockingServer getServer() {
        return mServer;
    }
    
    public void setServerScene(Scene scene) {
       Network._client = false;
       mScene = scene;
       mScene.onCreate();
    }
    
    public Scene getServerScene() {
        return mScene;
    }
    
}
