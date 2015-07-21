///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.main.Networking.Util;
//
//import java.io.IOException;
//import java.net.SocketException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Jack
// */
//public class UdpSocket extends Socket {
//    
//    public final int MAX_DATAGRAM_SIZE = 65507;
//    
//    protected char[] mBuffer;
//    protected java.net.DatagramSocket mSocket;
//    
//    public UdpSocket() {
//        mSocket = null;
//    }
//    
//    public int getLocalPort() {
//        if(mSocket == null) {
//            return 0;
//        }
//        return mSocket.getLocalPort();
//    }
//    
//    public String getLocalAddress() {
//        if(mSocket == null) {
//            return "";
//        }
//        return mSocket.getLocalAddress().toString();
//    }
//    
//    public int getRemotePort() {
//        if(mSocket == null) {
//            return 0;
//        }
//        return mSocket.getPort();
//    }
//    
//    public String getRemoteAddress() {
//        if(mSocket == null) {
//            return "";
//        }
//        return mSocket.getRemoteSocketAddress().toString();
//    }
//    
//    public Status bind(int port) {
//        
//    }
//    
//    public Status send(byte[] data, int size, String remoteAddress, int remotePort) {
//        
//    }
//    
//    public Status receive() {
//        
//    }
//    
//    public void unbind() {
//        try {
//            close();
//        } catch (IOException ex) {
//            Logger.getLogger(UdpSocket.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    @Override
//    protected void create() throws SocketException {
//        
//        if(mSocket == null) {
//            
//            mSocket = new java.net.DatagramSocket();
//            
//            setBlocking(mIsBlocking);
//            
//            // Enable SO_BROADCAST
//            mSocket.setBroadcast(true);
//            
//        }
//        
//    }
//    
//    @Override
//    public void setBlocking(boolean blocking) {
//        
//        super.setBlocking(blocking);
//        
//        if(mSocket != null) {
//            
//        }
//        
//    }
//    
//    @Override
//    public void close() throws IOException {
//        if(mSocket != null) {
//            mSocket.close();
//            mSocket = null;
//        }
//    }
//    
//}
