/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.network;

import com.dinasgames.engine.network.NonBlockingServer.Socket;

/**
 *
 * @author Jack
 */
public class Packet {
    
    public byte getID() {
        return (byte)0;
    }
    
    public void onServerWrite(Socket socket, Buffer buffer) throws Exception {
        buffer.writeByte(getID());
    }
    
    public void onServerRead(Socket socket, Buffer buffer) throws Exception {
        
    }
    
    public void onClientWrite(NonBlockingClient socket, Buffer buffer) throws Exception {
        buffer.writeByte(getID());
    }
    
    public void onClientRead(NonBlockingClient socket, Buffer buffer) throws Exception {
        
    }
    
}
