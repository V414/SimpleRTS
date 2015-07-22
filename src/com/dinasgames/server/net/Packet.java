/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.server.net;

import com.dinasgames.server.net.NonBlockingServer.Socket;

/**
 *
 * @author Jack
 */
public class Packet {
    
    public byte getID() {
        return (byte)0;
    }
    
    public void onServerWrite(Socket socket, Buffer buffer) {
        buffer.writeByte(getID());
    }
    
    public void onServerRead(Socket socket, Buffer buffer) {
        
    }
    
    public void onClientWrite(NonBlockingClient socket, Buffer buffer) {
        buffer.writeByte(getID());
    }
    
    public void onClientRead(NonBlockingClient socket, Buffer buffer) {
        
    }
    
}
