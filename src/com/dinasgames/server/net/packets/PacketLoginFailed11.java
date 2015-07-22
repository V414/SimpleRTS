/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.server.net.packets;

import com.dinasgames.main.Version;
import com.dinasgames.server.net.Buffer;
import com.dinasgames.server.net.NonBlockingClient;
import com.dinasgames.server.net.Packet;

/**
 *
 * @author Jack
 */
public class PacketLoginFailed11 extends Packet {
    
    String reason;
    
    public PacketLoginFailed11() {
        reason = "No Reason";
    }
    
    public PacketLoginFailed11(String reason) {
        this.reason = reason;
    }
    
    @Override
    public byte getID() {
        return (byte)11;
    }
    
    @Override
    public void onClientRead(NonBlockingClient socket, Buffer buffer) throws Exception {
        
        super.onClientRead(socket, buffer);
        
        // Display message
        System.out.println("Server kicked client. Reason: " + buffer.readString());
        
        // Disconnect from server
        socket.disconnect();
        
    }
    
}
