//package com.dinasgames.server;
//
//import com.dinasgames.server.net.NonBlockingClient;
//import com.dinasgames.server.net.NonBlockingServer;
//import com.dinasgames.main.Games.Game;
//import com.dinasgames.main.System.Clock;
//import com.dinasgames.server.net.Buffer;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.channels.ClosedChannelException;
//import java.util.concurrent.ExecutionException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class Main {
//    
//    public static void main(String args[]) {
//        
//        try {
//            Clock clock = new Clock();
//            Clock clock2 = new Clock();
//            
//            // Create a server
//            NonBlockingServer server = new NonBlockingServer(new NonBlockingServer.Listener() {
//
//                @Override
//                public void serverStarted() {
//                    System.out.println("Server started!");
//                }
//
//                @Override
//                public void serverStopped() {
//                    System.out.println("Server stopped.");
//                }
//
//                @Override
//                public void socketConnected(NonBlockingServer.Socket socket) {
//                    System.out.println("Server socket " + socket.getId() + " connected!");
//                }
//
//                @Override
//                public void socketDisconnected(NonBlockingServer.Socket socket) {
//                    System.out.println("Server socket " + socket.getId() + " disconnected!");
//                }
//
//                @Override
//                public void socketMessage(NonBlockingServer.Socket socket, Buffer message) {
//                    
//                    System.out.println("Server socket message!");
//                    System.out.println("Header: " + message.readShort());
//                    System.out.println("Content: " + message.readString());
//                    
//                    try {
//                        socket.send(new Buffer().writeShort((short)1).writeString("Hello Client!"));
//                    } catch (ClosedChannelException ex) {
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    
//                }
//
//                @Override
//                public void serverStarting() {
//                    System.out.println("Starting server...");
//                }
//
//                @Override
//                public void serverStopping() {
//                    System.out.println("Stopping server...");
//                }
//            }).listen(12000);
//            
//            NonBlockingClient[] clients = new NonBlockingClient[10];
//            
//            for(int i = 0; i < 10; i ++) {
//                
//                // Create a client
//                clients[i] = new NonBlockingClient(new NonBlockingClient.Listener() {
//
//                    @Override
//                    public void socketConnected(NonBlockingClient client) {
//                        try {
//                            System.out.println("Client connected to " + client.getRemoteAddress() + ".");
//                        } catch (IOException ex) {
//                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//
//                    @Override
//                    public void socketDisconnected(NonBlockingClient client) {
//                        System.out.println("Client disconnected from server.");
//                    }
//
//                    @Override
//                    public void socketMessage(NonBlockingClient client, Buffer buffer) {
//
//                        System.out.println("Client socket message!");
//                        System.out.println("Header: " + buffer.readShort());
//                        System.out.println("Content: " + buffer.readString());
//
//                        try {
//                            client.send(new Buffer().writeShort((short)1).writeString("Hello Server!"));
//                        } catch (ClosedChannelException ex) {
//                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//
//                    }
//
//                    @Override
//                    public void socketConnecting(NonBlockingClient client) {
//                        System.out.println("Client is connecting to server...");
//                    }
//
//                    @Override
//                    public void socketDisconnecting(NonBlockingClient client) {
//                        System.out.println("Client is disconnecting from server...");
//                    }
//                })
//                .connect("127.0.0.1", 12000)
//                .send(new Buffer().writeShort((short)1).writeString("Hello Server!"));
//                
//            }
//            
//            boolean disconnectedClients = false;
//            
//            while(true) {
//                
//                server.update();
//                for(int i = 0; i < 10; i++) {
//                    clients[i].update();
//                }
//                
//                if(clock.getElapsedTime().asSeconds() >= 4.f) {
//                    System.out.println("Server alive!");
//                    clock.restart();
//                }
//                
//                if(clock2.getElapsedTime().asSeconds() >= 10.f && !disconnectedClients) {
//                    System.out.println("Disconnecting clients...");
//                    for(int i = 0; i < 10; i++) {
//                        clients[i].disconnect();
//                    }
//                    disconnectedClients = true;
//                }
//                
//                if(clock2.getElapsedTime().asSeconds() >= 15.f) {
//                    System.out.println("Shutting down server...");
//                    server.close();
//                    break;
//                }
//                
//            }
//        } catch (ClosedChannelException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            
//           
//        
//    };
//    
//}
