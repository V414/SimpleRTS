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
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack
 */
public class NonBlockingServer {
    
    protected ServerSocketChannel mServerChannel;
    protected Selector mSelector;
    
    protected Map<SocketChannel, byte[]> dataTracking = new HashMap<SocketChannel, byte[]>();
    
    public NonBlockingServer() {
        mSelector = null;
        mServerChannel = null;
    }
    
    public void listen( int port ) {
        
        // Ensure we haven't already made a selector
        if(mSelector != null) {
            return;
        }
        
        // Ensure we haven't already made a server channel
        if(mServerChannel != null) {
            return;
        }
        
        try {
            
            mSelector = Selector.open();
            mServerChannel = ServerSocketChannel.open();
            mServerChannel.configureBlocking(false);
            mServerChannel.socket().bind(new InetSocketAddress("127.0.0.1", port));
            
            mServerChannel.register(mSelector, SelectionKey.OP_ACCEPT);
            
        } catch( IOException e ) {
            e.printStackTrace();
        }
        
    }
    
    public void close() {
        
        System.out.println("Shutting down...");
        
        try {

            if(mSelector != null) {
                mSelector.close();
                mSelector = null;
            }

            if(mServerChannel != null) {
                mServerChannel.socket().close();
                mServerChannel.close();
                mServerChannel = null;
            }
        
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public void update() {
        
        if(mSelector == null || mServerChannel == null) {
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
                
                // Accept new clients
                if(key.isAcceptable())  {
                    System.out.println("server accept");
                    onAccept(key);
                    continue;
                }
                
                // Write
                if(key.isWritable()) {
                    System.out.println("server write");
                    onWrite(key);
                    continue;
                }
                
                // Read
                if(key.isReadable()) {
                    System.out.println("server read");
                    onRead(key);
                    continue;
                }
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(NonBlockingServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //close();
        }
        
    }
    
    protected void onWrite(SelectionKey key) throws IOException {
        
        SocketChannel channel = (SocketChannel)key.channel();
        
        byte[] data = dataTracking.get(channel);
        dataTracking.remove(channel);
        
        channel.write(ByteBuffer.wrap(data));
        
        key.interestOps(SelectionKey.OP_READ);
        
    }
    
    protected void onAccept(SelectionKey key) throws IOException {
        
        ServerSocketChannel serverSocketchannel = (ServerSocketChannel)key.channel();
        SocketChannel socketChannel = (SocketChannel)serverSocketchannel.accept();
        socketChannel.configureBlocking(false);
        
        socketChannel.register(mSelector, SelectionKey.OP_WRITE);
        
        byte[] hello = new String("Hello from server").getBytes();
        dataTracking.put(socketChannel, hello);
        
    }
    
    protected void onRead(SelectionKey key) throws IOException {
        
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        readBuffer.clear();
        int read = -1;
        
        try {
            read = channel.read(readBuffer);
        } catch( IOException e ) {
            System.out.println("Reading problem. closing connection");
            key.cancel();
            channel.close();
        }
        
        if(read == -1) {
            System.out.println("Nothing to read. Closing connection");
            channel.close();
            key.cancel();
            return;
        }
        
        readBuffer.flip();
        
        byte[] data = new byte[1000];
        readBuffer.get(data, 0, read);
        System.out.println("Server received: " + new String(data));
        
        echo(key, data);
        
    }
    
    protected void echo(SelectionKey key, byte[] data) {
        SocketChannel socketChannel = (SocketChannel)key.channel();
        dataTracking.put(socketChannel, data);
        key.interestOps(SelectionKey.OP_WRITE);
    }
    
}
