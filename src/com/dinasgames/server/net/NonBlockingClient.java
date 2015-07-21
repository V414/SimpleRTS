/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.server.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 *
 * @author Jack
 */
public class NonBlockingClient {
    
    public static String message = "Hello from Client!";
    
    protected Selector mSelector;
    protected SocketChannel mSocket;
    protected boolean mConnected;
    
    public NonBlockingClient() {
        mSelector = null;
        mSocket = null;
        mConnected = false;
    }
    
    public boolean isConnected() {
        return mConnected;
    }
    
    public void connect(String address, int port) {
        
        if(mSocket != null) {
            return;
        }
        
        try {
            
            mSelector = Selector.open();
            mSocket = SocketChannel.open();
            mSocket.configureBlocking(false);
            
            mSocket.register(mSelector, SelectionKey.OP_CONNECT);
            mSocket.connect(new InetSocketAddress(address, port));
            
        } catch( Exception e ) {
            e.printStackTrace();
        } finally {
            //disconnect();
        }
    }
    
    public void disconnect() {
        
        System.out.println("Disconnecting...");
        
        try {
            
            if(mSocket != null) {
                mSocket.socket().close();
                mSocket.close();
                mSocket = null;
            }
            
            if(mSelector != null) {
                mSelector.close();
                mSelector = null;
            }
            
        } catch( IOException e ) {
            e.printStackTrace();
        }
        
        mConnected = false;
        
    }
    
    public void update() {
        
        if(mSelector == null || mSocket == null) {
            return;
        }
        
        try {
            
            if(mSelector.selectNow() <= 0) {
                return;
            }
            
            Iterator<SelectionKey> keys = mSelector.selectedKeys().iterator();
            while(keys.hasNext()) {
                
                SelectionKey key = keys.next();
                keys.remove();
                
                if(!key.isValid()) {
                    continue;
                }
                
                if(key.isConnectable()) {
                    System.out.println("client connect");
                    onConnect(key);
                    continue;
                }
                
                if(key.isWritable()) {
                    System.out.println("client write");
                    onWrite(key);
                    continue;
                }
                
                if(key.isReadable()) {
                    System.out.println("client read");
                    onRead(key);
                    continue;
                }
                
            }
            
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            //disconnect();
        }
        
    }
    
    protected void onConnect(SelectionKey key) throws IOException {
        
        SocketChannel channel = (SocketChannel)key.channel();
        
        if(channel.isConnectionPending()) {
            channel.finishConnect();
        }
        
        mConnected = true;
        
        channel.configureBlocking(false);
        channel.register(mSelector, SelectionKey.OP_WRITE);
        
    }
    
    protected void onWrite(SelectionKey key) throws IOException {
        
        SocketChannel channel = (SocketChannel)key.channel();
        channel.write(ByteBuffer.wrap(message.getBytes()));
        
        key.interestOps(SelectionKey.OP_READ);
        
    }
    
    protected void onRead(SelectionKey key) throws IOException {
        
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(1000);
        readBuffer.clear();
        int length = -1;
        
        try {
            length = channel.read(readBuffer);
        } catch(IOException e) {
            System.out.println("Reading problem " + e.toString());
            key.cancel();
            disconnect();
            return;
        }
        
        if(length == -1) {
            System.out.println("Nothing read from server");
            disconnect();
            key.cancel();
            return;
        }
        
        readBuffer.flip();
        
        byte[] buff = new byte[1024];
        readBuffer.get(buff, 0, length);
        System.out.println("Server said: " + new String(buff));
        
        key.interestOps(SelectionKey.OP_WRITE);
        
    }
    
}
