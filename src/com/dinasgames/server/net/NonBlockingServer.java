/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.server.net;

import com.dinasgames.main.System.Clock;
import com.dinasgames.main.System.Time;
import com.dinasgames.main.System.Timer;
import com.dinasgames.main.Version;
import com.dinasgames.server.net.packets.PacketKeepAlive244;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
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
    
    //protected Map<SocketChannel, Buffer> dataTracking = new HashMap<SocketChannel, Buffer>();
    
    
    public class Socket {
        
        protected NonBlockingServer mServer;
        protected Timer mPingTimer;
        protected Clock mPingClock;
        protected int mID;
        protected Buffer mPendingBuffer;
        protected SocketChannel mChannel;
        protected Selector mSelector;
        Time mPing;
        
        public void close() throws IOException {
            mServer.closeChannel(mChannel);
        }
        
        public Socket(NonBlockingServer server) {
            
            mServer = server;
            mID = -1;
            mChannel = null;
            mSelector = null;
            mPendingBuffer = new Buffer();
            mPing = new Time();
            
            mPingTimer = Timer.every(Time.seconds(1.f), () -> {
                
                try {
                    this.send(new PacketKeepAlive244());
                } catch (Exception ex) {
                    Logger.getLogger(NonBlockingServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println("Client (" + getId() + ") Ping: " + getPing().asMilliseconds() + "ms");
                
            });
            
            
        }
        
        public Socket(NonBlockingServer server, int id, SocketChannel channel, Selector selector) throws ClosedChannelException {
            
            this(server);
            
            // New socket connected
            mID = id;
            mChannel = channel;
            mSelector = selector;
            
            // Register an interest in the next message from this socket
            mChannel.register(mSelector, SelectionKey.OP_READ);
            
        }
        
        public Socket startPingTimer() {
            mPingClock = new Clock();
            return this;
        }

        public Socket stopPingTimer() {
            mPing = mPingClock.getElapsedTime();
            mPingTimer = null;
            return this;
        }
        
        public Time getPing() {
            return mPing;
        }
        
        public int getId() {
            return mID;
        }
        
        public String getRemoteAddress() throws IOException {
            return mChannel.getRemoteAddress().toString();
        }
        
        public String getLocalAddress() throws IOException {
            return mChannel.getLocalAddress().toString();
        }
        
        public Socket send(Packet packet) throws Exception {
            if(packet == null) {
                return this;
            }
            Buffer tmp = new Buffer();
            packet.onServerWrite(this, tmp);
            return send(tmp);
        }
        
        public Socket send(Buffer buffer) throws Exception {
            
            // Write to temporary buffer
            mPendingBuffer.writeBuffer(buffer);
            
            // Notify selector
            mChannel.register(mSelector, SelectionKey.OP_WRITE);
            
            return this;
        }
        
        public Buffer getPendingBuffer() {
            return mPendingBuffer;
        }
        
        public void onDestroy() {
            mPingTimer.stop();
        }
        
        public Listener getListener() {
            return mServer.getListener();
        }
        
        public NonBlockingServer getServer() {
            return mServer;
        }
        
    };
    
    public interface Listener {
        
        // Server events
        public void serverStarting();
        public void serverStarted();
        public void serverStopped();
        public void serverStopping();
        
        // Socket events
        public void socketConnected(Socket socket);
        public void socketDisconnected(Socket socket);
        //public void socketMessage(Socket socket, Buffer message);
        
        // Clients
        public void clientPacket(Socket socket, Packet packet);
        public void clientLoginSuccess(Socket socket, String name, Version version);
        public void clientLoginFailure(Socket socket, String reason);
        
    };
    
    public final int MAX_SOCKETS = 128;
    
    protected Map<Byte, Packet> mPacketMap = new HashMap();
    protected Map<SocketChannel, Socket> mSocketMap = new HashMap();
    protected ServerSocketChannel mServerChannel;
    protected Selector mSelector;
    protected Listener mListener;
    protected boolean mRunning;
    protected boolean[] mSocketSlot;
    
    public NonBlockingServer() {
        mSelector = null;
        mServerChannel = null;
        mListener = null;
        mRunning = false;
        mSocketSlot = new boolean[MAX_SOCKETS];
        for(int i = 0; i < MAX_SOCKETS; i++) {
            mSocketSlot[i] = false;
        }
    }
    
    public Listener getListener() {
        return mListener;
    }
    
    public NonBlockingServer register(Packet packet) {
        mPacketMap.put(packet.getID(), packet);
        return this;
    }
    
    public NonBlockingServer unregister(Packet packet) {
        mPacketMap.remove(packet);
        return this;
    }
    
    protected int newSlot() {
        for(int i = 0; i < MAX_SOCKETS; i++) {
            if(!mSocketSlot[i]) {
                mSocketSlot[i] = true;
                return i;
            }
        }
        return -1;
    }
    
    protected void removeSlot(int i) {
        if(i < 0 || i >= MAX_SOCKETS) {
            return;
        }
        mSocketSlot[i] = false;
    }
    
    public NonBlockingServer(Listener listener) {
        this();
        mListener = listener;
    }
    
    public NonBlockingServer setListener(Listener listener) {
        mListener = listener;
        return this;
    }
    
    public NonBlockingServer listen( int port ) {
        
        if(mRunning) {
            return this;
        }
        
        // Ensure we haven't already made a selector
        if(mSelector != null) {
            return this;
        }
        
        // Ensure we haven't already made a server channel
        if(mServerChannel != null) {
            return this;
        }
        
        if(mListener != null) {
            mListener.serverStarting();
        }
        
        try {
            
            mSelector = Selector.open();
            mServerChannel = ServerSocketChannel.open();
            mServerChannel.configureBlocking(false);
            mServerChannel.socket().bind(new InetSocketAddress("127.0.0.1", port));
            
            mServerChannel.register(mSelector, SelectionKey.OP_ACCEPT);
            
            mRunning = true;
            
            if(mListener != null) {
                mListener.serverStarted();
            }
            
        } catch( IOException e ) {
            e.printStackTrace();
        }
        
        return this;
        
    }
    
    public boolean isRunning() {
        return mRunning;
    }
    
    public NonBlockingServer close() {
        
        if(!mRunning) {
            return this;
        }
        
        if(mListener != null) {
            mListener.serverStopping();
        }
        
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
        
        mRunning = false;
        
        if(mListener != null) {
            mListener.serverStopped();
        }
        
        return this;
        
    }
    
    public NonBlockingServer update() throws Exception {
        
        if(mSelector == null || mServerChannel == null) {
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

                    // Accept new clients
                    if(key.isAcceptable())  {
                        onAccept(key);
                        continue;
                    }

                    // Write
                    if(key.isWritable()) {
                        onWrite(key);
                        continue;
                    }

                    // Read
                    if(key.isReadable()) {
                        onRead(key);
                        continue;
                    }

                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(NonBlockingServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //close();
        }
        
        return this;
        
    }
    
    protected void onWrite(SelectionKey key) throws IOException {
        
        SocketChannel channel = (SocketChannel)key.channel();
        
        Socket socket = mSocketMap.get(channel);
        Buffer pending = socket.getPendingBuffer();
        
        if(pending.empty()) {
            // Nothing to write
            key.interestOps(SelectionKey.OP_READ);
            return;
        }
        
        // Write the data to the stream
        channel.write(pending.getByteBuffer());
        
        // Clear the pending data
        pending.clear();
        
//        Buffer data = dataTracking.get(channel);
//        dataTracking.remove(channel);
//        
//        channel.write(data.getByteBuffer());
        
        //channel.write(ByteBuffer.wrap(data));
        
        key.interestOps(SelectionKey.OP_READ);
        
    }
    
    protected void onAccept(SelectionKey key) throws IOException {
        
        ServerSocketChannel serverSocketchannel = (ServerSocketChannel)key.channel();
        SocketChannel socketChannel = (SocketChannel)serverSocketchannel.accept();
        socketChannel.configureBlocking(false);
        
        Socket newSocket = new Socket(this, newSlot(), socketChannel, mSelector);
        
        if(newSocket.getId() < 0) {
            // No more space on the server
            closeChannel(socketChannel);
            return;
        }
        
        // Keep track of this socket
        mSocketMap.put(socketChannel, newSocket);
        
        if(mListener != null) {
            mListener.socketConnected(newSocket);
        }
        
//        socketChannel.register(mSelector, SelectionKey.OP_WRITE);
        
        //byte[] hello = new String("Hello from server").getBytes();
        //dataTracking.put(socketChannel, hello);
        
//        Buffer hello = new Buffer();
//        hello.writeShort((short)1);
//        hello.writeString("Hello Client");
//        
//        dataTracking.put(socketChannel, hello);
        
    }
    
    protected void onRead(SelectionKey key) throws Exception {
        
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        readBuffer.clear();
        int read = -1;
        
        try {
            read = channel.read(readBuffer);
        } catch( IOException e ) {
            key.cancel();
            closeChannel(channel);
            return;
        }
        
        if(read == -1) {
            key.cancel();
            closeChannel(channel);
            return;
        }
        
        Socket thisSocket = mSocketMap.get(channel);
        Buffer buffer = new Buffer(readBuffer);
        
        while(buffer.getReadPosition() < buffer.size()) {
            byte head = buffer.readByte();
            if(mPacketMap.containsKey(head)) {
                Packet packet = mPacketMap.get(head);
                packet.onServerRead(thisSocket, buffer);
                if(mListener != null) {
                    mListener.clientPacket(thisSocket, packet);
                }
            }
        }
        
        // Handle callback
//        if(mListener != null) {
//            buffer.setReadPosition(0);
//            mListener.socketMessage(thisSocket, buffer);
//        }
        
//        Buffer buffer = new Buffer(readBuffer);
//        
//        short header = buffer.readShort();
//        
//        if(header == 1) {
//            System.out.println(buffer.readString());
//        }
        
        //echo(key,buffer);
        
//        readBuffer.flip();
//        
//        byte[] data = new byte[1000];
//        readBuffer.get(data, 0, read);
//        System.out.println("Server received: " + new String(data));
//        
//        echo(key, data);
        
    }
    
    protected void closeChannel(SocketChannel channel) throws IOException {
        Socket socket = mSocketMap.get(channel);
        if(socket != null) {
            if(mListener != null) {
                mListener.socketDisconnected(socket);
            }
            socket.onDestroy();
            removeSlot(socket.getId());
            mSocketMap.remove(channel, socket);
            channel.close();
        }
    }
    
//    protected void echo(SelectionKey key, Buffer data) {
//        SocketChannel socketChannel = (SocketChannel)key.channel();
//        dataTracking.put(socketChannel, new Buffer(data));
//        key.interestOps(SelectionKey.OP_WRITE);
//    }
    
}
