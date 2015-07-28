/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.server.net.packets;

import com.dinasgames.main.inputs.Input;
import com.dinasgames.main.math.Vector2f;
import com.dinasgames.server.net.Buffer;
import com.dinasgames.server.net.NonBlockingClient;
import com.dinasgames.server.net.NonBlockingServer;
import com.dinasgames.server.net.Packet;

/**
 *
 * @author Jack
 */
public class PacketInput20 extends Packet {
    
    Input input;
    
    public PacketInput20() {
        
    }
    
    public PacketInput20(Input input) {
        this.input = input;
    }
    
    public Input getInput() {
        return input;
    }
    
    @Override
    public byte getID() {
        return (byte)20;
    }

    @Override
    public void onServerWrite(NonBlockingServer.Socket socket, Buffer buffer) throws Exception {
        
        super.onServerWrite(socket, buffer);
        
        byte flag = 0;
        
        
        
        //buffer.writeBoolean( (0x01) );
        
    }

    @Override
    public void onServerRead(NonBlockingServer.Socket socket, Buffer buffer) throws Exception {
        
        super.onServerRead(socket, buffer);

    }

    @Override
    public void onClientWrite(NonBlockingClient socket, Buffer buffer) throws Exception {
        
        super.onClientWrite(socket, buffer);
        
    }

    @Override
    public void onClientRead(NonBlockingClient socket, Buffer buffer) throws Exception {
        
        super.onClientRead(socket, buffer);
        
    }
    
}
