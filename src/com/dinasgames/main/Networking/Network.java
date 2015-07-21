/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Networking;

import com.dinasgames.main.Networking.Packets.*;
//import com.esotericsoftware.kryo.Kryo;

/**
 *
 * @author Jack
 */
public class Network {
    
    public static final int TCP_PORT = 12000;
    public static final int UDP_PORT = 12001;
    public static boolean _client = true;
    
//    public static void loadPackets(Kryo k) {
//        
//        // Add packet classes
//        k.register(Packet244_Ping.class);
//        
//    }
    
    public static boolean isClient() {
        return _client;
    }
    
    public static boolean isServer() {
        return !_client;
    }
    
}
