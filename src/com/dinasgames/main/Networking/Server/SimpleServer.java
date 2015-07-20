/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Networking.Server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack
 */
public class SimpleServer {
    
    public final int TCP_PORT = 12000;
    public final int UDP_PORT = 12001;
    
    protected int mTcpPort, mUdpPort;
    protected Server mServer;
    
    public SimpleServer() {
        
        mServer = new Server();
        mTcpPort = TCP_PORT;
        mUdpPort = UDP_PORT;
        
        mServer.addListener(new Listener() {
            
            @Override
            public void received(Connection connection, Object object) {
                
            }
            
        });
    }
    
    protected boolean bind() {
        try {
            mServer.bind(mTcpPort, mTcpPort);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(SimpleServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void setTcpPort(int port) {
        mTcpPort = port;
    }
    
    public void setUdpPort(int port) {
        mUdpPort = port;
    }
    
    public int getTcpPort() {
        return mTcpPort;
    }
    
    public int getUdpPort() {
        return mUdpPort;
    }
    
    public boolean start() {
        mServer.start();
        return this.bind();
    }
    
    public void stop() {
        mServer.stop();
    }
    
}
