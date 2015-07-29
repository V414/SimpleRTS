/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.server.net.packets;

import com.dinasgames.engine.network.Buffer;
import com.dinasgames.engine.network.NonBlockingClient;
import com.dinasgames.engine.network.NonBlockingServer;
import com.dinasgames.engine.network.Packet;
import java.nio.channels.ClosedChannelException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack
 */
public class PacketKeepAlive244 extends Packet {
    
    @Override
    public byte getID() {
        return (byte)244;
    }

    @Override
    public void onServerWrite(NonBlockingServer.Socket socket, Buffer buffer) throws Exception {
        
        super.onServerWrite(socket, buffer);
        
        buffer.writeBoolean(true);
        socket.startPingTimer();
    }

    @Override
    public void onServerRead(NonBlockingServer.Socket socket, Buffer buffer) throws Exception {
        
        boolean isServer = buffer.readBoolean();
        
        if(isServer) {
            // Calculate ping
            socket.stopPingTimer();
        }else{
            try {
                // Send it back
                socket.send(new Buffer().writeByte(getID()).writeBoolean(isServer));
            } catch (ClosedChannelException ex) {
                Logger.getLogger(PacketKeepAlive244.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public void onClientWrite(NonBlockingClient socket, Buffer buffer) throws Exception {
        
        super.onClientWrite(socket, buffer);
        
        buffer.writeBoolean(false);
        socket.startPingTimer();
        
    }

    @Override
    public void onClientRead(NonBlockingClient socket, Buffer buffer) throws Exception {
        
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
                Logger.getLogger(PacketKeepAlive244.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
    
}
