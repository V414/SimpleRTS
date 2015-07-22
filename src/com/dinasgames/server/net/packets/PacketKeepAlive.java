/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.server.net.packets;

import com.dinasgames.main.Math.RandomNumber;
import com.dinasgames.main.System.Clock;
import com.dinasgames.server.net.Buffer;
import com.dinasgames.server.net.NonBlockingClient;
import com.dinasgames.server.net.NonBlockingServer;
import com.dinasgames.server.net.Packet;
import java.nio.channels.ClosedChannelException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack
 */
public class PacketKeepAlive extends Packet {
    
    @Override
    public byte getID() {
        return (byte)244;
    }

    @Override
    public void onServerWrite(NonBlockingServer.Socket socket, Buffer buffer) {
        
        super.onServerWrite(socket, buffer);
        
        buffer.writeBoolean(true);
        socket.startPingTimer();
    }

    @Override
    public void onServerRead(NonBlockingServer.Socket socket, Buffer buffer) {
        
        boolean isServer = buffer.readBoolean();
        
        if(isServer) {
            // Calculate ping
            socket.stopPingTimer();
        }else{
            try {
                // Send it back
                socket.send(new Buffer().writeByte(getID()).writeBoolean(isServer));
            } catch (ClosedChannelException ex) {
                Logger.getLogger(PacketKeepAlive.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public void onClientWrite(NonBlockingClient socket, Buffer buffer) {
        
        super.onClientWrite(socket, buffer);
        
        buffer.writeBoolean(false);
        socket.startPingTimer();
        
    }

    @Override
    public void onClientRead(NonBlockingClient socket, Buffer buffer) {
        
        super.onClientRead(socket, buffer);
        
        boolean isServer = buffer.readBoolean();
        
        if(!isServer) {
            // Calculate ping
            socket.stopPingTimer();
        }else{
            try {
                // Send it back
                socket.send(new Buffer().writeByte(getID()).writeBoolean(isServer));
            } catch (ClosedChannelException ex) {
                Logger.getLogger(PacketKeepAlive.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
    
}
