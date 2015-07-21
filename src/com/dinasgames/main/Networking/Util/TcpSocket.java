///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.main.Networking.Util;
//
//import com.dinasgames.main.System.Time;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.SocketException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Jack
// */
//public class TcpSocket extends Socket {
//    
//    protected DataInputStream mInput;
//    protected DataOutputStream mOutput;
//    protected PendingPacket mPendingPacket;
//    protected java.net.Socket mSocket;
//    
//    public TcpSocket() {
//        mSocket = null;
//        mOutput = null;
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
//    public String getRemoteAddress() {
//        if(mSocket == null) {
//            return "";
//        }
//        return mSocket.getRemoteSocketAddress().toString();
//    }
//    
//    public int getRemotePort() {
//        if(mSocket == null) {
//            return 0;
//        }
//        return mSocket.getPort();
//    }
//    
//    public void connect(String remoteAddress, int remotePort) {
//        connect(remoteAddress, remotePort, new Time());
//    }
//    
//    public void connect(String remoteAddress, int remotePort, Time timeout) {
//
//    }
//    
//    public void disconnect() {
//        try {
//            close();
//        } catch (IOException ex) {
//            Logger.getLogger(TcpSocket.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    /**
//     * 
//     * @param data
//     * @return bytes sent
//     * @throws IOException 
//     */
//    public int send(byte[] data) throws IOException {
//        
//        // Check that the socket is connected
//        if(mSocket == null) {
//            return 0;
//        }
//        
//        // Check the data is valid
//        if(data == null) {
//            return 0;
//        }
//        
//        // Write the bytes
//        mOutput.write(data);
//        
//    }
//    
//    public byte[] receive() {
//        
//        if(mSocket == null) {
//            return null;
//        }
//        
//        mInput.
//        
//    }
//    
//    /*public Packet receive() {
//        
//    }*/
//    
//    @Override
//    protected void create() throws SocketException {
//        
//        if(mSocket == null) {
//            
//            mSocket = new java.net.Socket();
//            mOutput = new DataOutputStream(mSocket.getOutputStream());
//            mInput = new DataInputStream(mSocket.getInputStream());
//            
//            setBlocking(mIsBlocking);
//            
//            // Disable Nagle algorithm
//            mSocket.setTcpNoDelay(false);
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
//            mInput.close();
//            mOutput.close();
//            mSocket.close();
//            mSocket = null;
//            mOutput = null;
//            mInput = null;
//        }
//    }
//    
//}
