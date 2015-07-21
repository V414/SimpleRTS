///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.main.Networking.Util;
//
//import java.io.IOException;
//import java.net.SocketAddress;
//import java.net.SocketException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Jack
// */
//public class TcpListener extends Socket {
//    
//    protected java.net.ServerSocket mListenSocket;
//    protected int mPort;
//    protected Status mStatus;
//    
//    public TcpListener() {
//        mListenSocket = null;
//        mPort = 0;
//    }
//    
//    public int getLocalPort() {
//        if(mListenSocket == null) {
//            return 0;
//        }
//        return mListenSocket.getLocalPort();
//    }
//    
//    public String getLocalAddress() {
//        if(mListenSocket == null) {
//            return "";
//        }
//        return mListenSocket.getLocalSocketAddress().toString();
//    }
//    
//    public void listen(int port) throws SocketException {
//        
//        mPort = port;
//        
//        create();
//
//        
//    }
//    
//    public TcpSocket accept() {
//        return null;
//    }
//    
//    @Override
//    protected void create() throws SocketException {
//        
//        if(mListenSocket == null) {
//            
//            try {
//                mListenSocket = new java.net.ServerSocket(mPort);
//                
//                setBlocking(mIsBlocking);
//                
//                // Disable Nagle algorithm
//            } catch (IOException ex) {
//                Logger.getLogger(TcpListener.class.getName()).log(Level.SEVERE, null, ex);
//                throw new SocketException(ex.toString());
//            }
//            
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
//
//        
//    }
//    
//    @Override
//    public void close() throws IOException {
//        if(mListenSocket != null) {
//            mListenSocket.close();
//            mListenSocket = null;
//        }
//    }
//    
//}
