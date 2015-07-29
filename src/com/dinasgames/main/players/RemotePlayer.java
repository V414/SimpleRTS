/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.players;

import com.dinasgames.main.controllers.RemoteController;
import com.dinasgames.engine.system.Time;
import com.dinasgames.engine.network.NonBlockingServer;
import com.dinasgames.engine.network.NonBlockingServer.Socket;
import com.dinasgames.server.net.packets.PacketInput20;

/**
 *
 * @author Jack
 */
public class RemotePlayer extends Player {
    
    Socket mSocket;
    
    public RemotePlayer(Socket socket) {
        
        mController = new RemoteController();
        mSocket     = socket;
        
    }
    
    @Override
    public void update(Time timePassed) {
        super.update(timePassed);
    }
    
    public Socket getSocket() {
        return mSocket;
    }
    
    public void readInput(PacketInput20 p) {
        
    }
    
}
