///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.main.Networking.Client;
//
//import com.dinasgames.main.Networking.Network;
//import com.esotericsoftware.kryonet.Client;
//import com.esotericsoftware.kryonet.Connection;
//import com.esotericsoftware.kryonet.Listener;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Jack
// */
//public class SimpleClient {
//    
//    protected Client mClient;
//    protected boolean mConnected;
//    protected int mTcpPort, mUdpPort;
//    
//    public SimpleClient() {
//        
//        mClient = new Client();
//        
//        Network.loadPackets(mClient.getKryo());
//        
//        mConnected = false;
//        mTcpPort = Network.TCP_PORT;
//        mUdpPort = Network.UDP_PORT;
//        
//        mClient.addListener(new Listener(){
//            
//            @Override
//            public void received(Connection connection, Object object) {
//                
//            }
//            
//        });
//        
//    }
//    
//    public void setTcpPort(int port) {
//        mTcpPort = port;
//    }
//    
//    public void setUdpPort(int port) {
//        mUdpPort = port;
//    }
//    
//    public boolean connect( String address ) {
//        
//        if(mConnected) {
//            return true;
//        }
//
//        try {
//            mClient.start();
//            mClient.connect(4000, address, mTcpPort);
//        } catch (IOException ex) {
//            Logger.getLogger(SimpleClient.class.getName()).log(Level.SEVERE, null, ex);
//            mConnected = false;
//            return false;
//        }
//        
//        mConnected = true;
//        return true;
//        
//    }
//    
//    public void disconnect() {
//        
//        if(!mConnected) {
//            return;
//        }
//        
//        mClient.close();
//        mClient.stop();
//        mConnected = false;
//        
//    }
//    
//    public boolean isConnected() {
//        return mConnected;
//    }
//    
//}
