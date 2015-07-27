/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.server.net.packets;

import com.dinasgames.main.system.Time;
import com.dinasgames.main.system.Timer;
import com.dinasgames.main.Version;
import com.dinasgames.server.net.Buffer;
import com.dinasgames.server.net.NonBlockingClient;
import com.dinasgames.server.net.NonBlockingServer;
import com.dinasgames.server.net.Packet;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack
 */
public class PacketLogin10 extends Packet {
    
    public String name;
    public Version version;
    
    public PacketLogin10() {
        version = null;
        name = "";
    }
    
    public PacketLogin10(String name, int a, int b, int c, int d) {
        this.name = name;
        this.version = new Version(a,b,c,d);
    }
    
    public PacketLogin10(String name, Version version) {
        this.name = name;
        this.version = version;
    }
    
    @Override
    public byte getID() {
        return (byte)10;
    }

    @Override
    public void onServerWrite(NonBlockingServer.Socket socket, Buffer buffer) throws Exception {
        
        super.onServerWrite(socket, buffer);
        
        buffer.writeInt(version.major);
        buffer.writeInt(version.minor);
        buffer.writeInt(version.build);
        buffer.writeInt(version.release);
        buffer.writeString(name);
    }

    @Override
    public void onServerRead(NonBlockingServer.Socket socket, Buffer buffer) throws Exception {
        
        super.onServerRead(socket, buffer);
        
        int a,b,c,d;
        a = buffer.readInt();
        b = buffer.readInt();
        c = buffer.readInt();
        d = buffer.readInt();
        
        this.version = new Version(a,b,c,d);
        this.name = buffer.readString();
        
        // Ensure this client has the same version as the server
        if(Version.compare(this.version, version)) {
            
            //System.out.println("Client " + socket.getId() + " logged in with name '" + name + "' and version " + this.version.toString());
            
            if(socket.getListener() != null) {
                socket.getListener().clientLoginSuccess(socket, this.name, this.version);
            }
            
            // Login successful, send the client our details
            socket.send(new PacketLogin10("server", Version.current));
            
            // Give them a place on the player list :D
            
            
        }else{
            
            String reason = "Incompatible version. Client = " + this.version.toString() + ", Server = " + Version.current.toString();
            
            //System.out.println("Client " + socket.getId() + " was kicked. Reason: " + reason);
            if(socket.getListener() != null) {
                socket.getListener().clientLoginFailure(socket, reason);
            }
            
            // This client is incompatible with the server
            socket.send(new PacketLoginFailed11(reason));
            
            // Kick client after some time. Let the kick message take effect
            Timer.after(Time.milliseconds(100), () -> {
                
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(PacketLogin10.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            
        }
        
    }

    @Override
    public void onClientWrite(NonBlockingClient socket, Buffer buffer) throws Exception {
        
        super.onClientWrite(socket, buffer);
        
        buffer.writeInt(version.major);
        buffer.writeInt(version.minor);
        buffer.writeInt(version.build);
        buffer.writeInt(version.release);
        buffer.writeString(name);
        
    }

    @Override
    public void onClientRead(NonBlockingClient socket, Buffer buffer) throws Exception {
        
        super.onClientRead(socket, buffer);
        
        int a,b,c,d;
        a = buffer.readInt();
        b = buffer.readInt();
        c = buffer.readInt();
        d = buffer.readInt();
        
        this.version = new Version(a,b,c,d);
        this.name = buffer.readString();
        
        if(Version.compare(this.version, Version.current)) {
            // Success!
            if(socket.getListener() != null) {
                socket.getListener().clientLoginSuccessful(socket, name, version);
            }
        }else{
            String reason = "Server is incompatible with client.";
            if(socket.getListener() != null) {
                socket.getListener().clientLoginFailure(socket, reason);
            }
            socket.disconnect();
        }
        
    }
    
}
