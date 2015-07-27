/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.server.net;

import com.dinasgames.main.system.Clock;
import com.dinasgames.main.system.Time;
import com.dinasgames.main.Version;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Jack
 */
public class NonBlockingClient {
    
    public interface Listener {
        
        // Socket
        public void socketConnecting(NonBlockingClient client);
        public void socketDisconnecting(NonBlockingClient client);
        
        public void socketConnected(NonBlockingClient client);
        public void socketDisconnected(NonBlockingClient client);
        //public void socketMessage(NonBlockingClient client, Buffer buffer);
        
        // Client
        public void clientPacket(NonBlockingClient client, Packet packet);
        public void clientLoginSuccessful(NonBlockingClient client, String serverName, Version serverVersion);
        public void clientLoginFailure(NonBlockingClient client, String reason);
        
    };
    
    protected Map<Byte, Packet> mPacketMap = new HashMap();
    protected Clock mPingTimer;
    protected Time mPing;
    protected Buffer mPendingBuffer;
    protected Selector mSelector;
    protected SocketChannel mSocket;
    protected boolean mConnected;
    protected Listener mListener;
    
    public NonBlockingClient() {
        mSelector = null;
        mSocket = null;
        mConnected = false;
        mListener = null;
        mPendingBuffer = new Buffer();
        mPing = new Time();
    }
    
    public NonBlockingClient(Listener listener) {
        this();
        mListener = listener;
    }
    
    public NonBlockingClient setListener(Listener listener) {
        mListener = listener;
        return this;
    }
    
    public Listener getListener() {
        return mListener;
    }
    
    public NonBlockingClient register(Packet packet) {
        mPacketMap.put(packet.getID(), packet);
        return this;
    }
    
    public NonBlockingClient unregister(Packet packet) {
        mPacketMap.remove(packet);
        return this;
    }
    
    public boolean isConnected() {
        return mConnected;
    }
    
    public Time getPing() {
        return mPing;
    }
    
    public NonBlockingClient startPingTimer() {
        mPingTimer = new Clock();
        return this;
    }
    
    public NonBlockingClient stopPingTimer() {
        mPing = mPingTimer.getElapsedTime();
        mPingTimer = null;
        return this;
    }
    
    public NonBlockingClient connect(String address, int port) {
        
        if(mConnected) {
            return this;
        }
        
        if(mSocket != null) {
            return this;
        }
        
        if(mListener != null) {
            mListener.socketConnecting(this);
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
        
        return this;
    }
    
    public NonBlockingClient disconnect() {
        
        if(!mConnected) {
            return this;
        }
        
        if(mListener != null) {
            mListener.socketDisconnecting(this);
        }
        
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
        
        if(mListener != null) {
            mListener.socketDisconnected(this);
        }
        
        return this;
        
    }
    
    public NonBlockingClient update() throws Exception {
        
        if(mSelector == null || mSocket == null) {
            return this;
        }
        
        try {
            
            while(mSelector.selectNow() > 0) {
            
                Iterator<SelectionKey> keys = mSelector.selectedKeys().iterator();
                while(keys.hasNext()) {

                    SelectionKey key = keys.next();
                    keys.remove();

                    if(!key.isValid()) {
                        continue;
                    }

                    if(key.isConnectable()) {
                        onConnect(key);
                        continue;
                    }

                    if(key.isWritable()) {
                        onWrite(key);
                        continue;
                    }

                    if(key.isReadable()) {
                        onRead(key);
                        continue;
                    }

                }
            }
            
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            //disconnect();
        }
        
        return this;
        
    }
    
    protected void onConnect(SelectionKey key) throws IOException {
        
        SocketChannel channel = (SocketChannel)key.channel();
        
        if(channel.isConnectionPending()) {
            channel.finishConnect();
        }
        
        channel.configureBlocking(false);
        
        mConnected = true;
        
        if(mListener != null) {
            mListener.socketConnected(this);
        }
        
        if(mPendingBuffer.empty()) {
            channel.register(mSelector, SelectionKey.OP_READ);
        }else{
            channel.register(mSelector, SelectionKey.OP_WRITE);
        }
        
    }
    
    protected void onWrite(SelectionKey key) throws IOException {
        
        // If we have nothing to write then continue
        if(mPendingBuffer.empty()) {
            //key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            key.interestOps(SelectionKey.OP_READ);
            return;
        }
        
        // Otherwise write the data
        SocketChannel channel = (SocketChannel)key.channel();
        //channel.write(ByteBuffer.wrap(message.getBytes()));
        channel.write(mPendingBuffer.getByteBuffer());
        mPendingBuffer.clear();
        
        // Continue
        key.interestOps(SelectionKey.OP_READ);
        //key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        
    }
    
    protected void onRead(SelectionKey key) throws Exception {
        
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(1000);
        readBuffer.clear();
        int length = -1;
        
        try {
            length = channel.read(readBuffer);
        } catch(IOException e) {
            key.cancel();
            disconnect();
            return;
        }
        
        if(length == -1) {
            disconnect();
            key.cancel();
            return;
        }
        
        Buffer buffer = new Buffer(readBuffer);
        
        while(buffer.getReadPosition() < buffer.size()) {
            byte head = buffer.readByte();
            if(mPacketMap.containsKey(head)) {
                Packet packet = mPacketMap.get(head);
                packet.onClientRead(this, buffer);
                if(mListener != null) {
                    mListener.clientPacket(this, packet);
                }
            }
        }
        
//        if(mListener != null) {
//            buffer.setReadPosition(0);
//            mListener.socketMessage(this, new Buffer(readBuffer));
//        }
        
        
//        readBuffer.flip();
        
//        byte[] buff = new byte[1024];
//        readBuffer.get(buff, 0, length);
//        System.out.println("Server said: " + new String(buff));
        
        //key.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        
    }
    
    public NonBlockingClient send(Packet p) throws Exception {
        if(p == null) {
            return this;
        }
        Buffer tmp = new Buffer();
        p.onClientWrite(this, tmp);
        return send(tmp);
    }
    
    public NonBlockingClient send(Buffer b) throws ClosedChannelException {
        mPendingBuffer.writeBuffer(b);
        if(isConnected()) {
            mSocket.register(mSelector, SelectionKey.OP_WRITE);
        }
        return this;
    }
    
    public String getRemoteAddress() throws IOException {
        return mSocket.getRemoteAddress().toString();
    }
    
    public String getLocalAddress() throws IOException {
        return mSocket.getLocalAddress().toString();
    }
    
}
