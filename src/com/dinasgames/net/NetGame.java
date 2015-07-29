/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.net;

import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.Font;
import com.dinasgames.lwjgl.util.GL;
import com.dinasgames.lwjgl.util.Mouse;
import com.dinasgames.lwjgl.util.RectangleShape;
import com.dinasgames.lwjgl.util.Text;
import com.dinasgames.lwjgl.util.Texture;
import com.dinasgames.main.Version;
import com.dinasgames.main.games.ClientServerGame;
import com.dinasgames.main.games.WindowGame;
import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.system.Time;
import com.dinasgames.main.system.Timer;
import com.dinasgames.server.net.NonBlockingClient;
import com.dinasgames.server.net.NonBlockingServer;
import com.dinasgames.server.net.Packet;
import com.dinasgames.server.net.packets.PacketKeepAlive244;
import com.dinasgames.server.net.packets.PacketLogin10;
import com.dinasgames.server.net.packets.PacketLoginFailed11;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Jack
 */
public class NetGame extends WindowGame {
    
    Timer pingTimer;
    NonBlockingServer server;
    NonBlockingClient client;
    
    StateValue x,y;
    RectangleShape shape, shapePast, shapeFuture;
    
    Text text;
    Font font;
    
    @Override
    public void load() {
        super.load();
        
        server = new NonBlockingServer(new NonBlockingServer.Listener() {

                @Override
                public void serverStarted() {
                    System.out.println("Server started!");
                }

                @Override
                public void serverStopped() {
                    System.out.println("Server stopped.");
                }

                @Override
                public void socketConnected(NonBlockingServer.Socket socket) {
                    System.out.println("Server socket " + socket.getId() + " connected!");
                }

                @Override
                public void socketDisconnected(NonBlockingServer.Socket socket) {
                    System.out.println("Server socket " + socket.getId() + " disconnected!");
                }

                @Override
                public void serverStarting() {
                    System.out.println("Starting server...");
                }

                @Override
                public void serverStopping() {
                    System.out.println("Stopping server...");
                }

                @Override
                public void clientPacket(NonBlockingServer.Socket socket, Packet packet) {
                    
                    switch(packet.getID()) {
                        
                        default: 
                            // Ignore other IDs
                            break;
                            
                    }
                    
                }

                @Override
                public void clientLoginSuccess(NonBlockingServer.Socket socket, String name, Version version) {
                    
                    System.out.println("Client " + socket.getId() + " logged in with name '" + name + "' and version " + version);
                    
                }

                @Override
                public void clientLoginFailure(NonBlockingServer.Socket socket, String reason) {
                    
                    System.out.println("Client " + socket.getId() + " failed to login. Reason: " + reason);
                    
                }


            })
                .register(new PacketKeepAlive244())
                .register(new PacketLogin10())
                .listen(12000);
        
        client = new NonBlockingClient(new NonBlockingClient.Listener() {
                
                @Override
                public void socketConnected(NonBlockingClient client) {
                    try {
                        System.out.println("Client connected to " + client.getRemoteAddress() + ". Logging in...");
                        client.send(new PacketLogin10("Client Name", Version.current));
                    } catch (Exception ex) {
                        Logger.getLogger(com.dinasgames.server.Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                @Override
                public void socketDisconnected(NonBlockingClient client) {
                    System.out.println("Client disconnected from server.");
                }
                
                @Override
                public void socketConnecting(NonBlockingClient client) {
                    System.out.println("Client is connecting to server...");
                }
                
                @Override
                public void socketDisconnecting(NonBlockingClient client) {
                    System.out.println("Client is disconnecting from server...");
                }

                @Override
                public void clientPacket(NonBlockingClient client, Packet packet) {
                    
                    switch(packet.getID()) {
                        
                        default:
                            // Ignore
                            break;
                        
                    }
                    
                }

                @Override
                public void clientLoginSuccessful(NonBlockingClient client, String serverName, Version serverVersion) {
                    
                    System.out.println("Client logged into server successfully! Server: " + serverName + " " + serverVersion);
                    
                }

                @Override
                public void clientLoginFailure(NonBlockingClient client, String reason) {
                    
                    System.out.println("Client failed to login to server. Reason: " + reason);
                    
                }
            })
                    .register(new PacketKeepAlive244())
                    .register(new PacketLogin10())
                    .register(new PacketLoginFailed11())
                    .connect("127.0.0.1", 12000);

        // Send ping to the server
        pingTimer = Timer.every(Time.seconds(1.f), () -> {
            
            if(client.isConnected()) {
                try {
                    client.send(new PacketKeepAlive244());
                } catch (Exception ex) {
                    Logger.getLogger(ClientServerGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            System.out.println("Client ping: " + client.getPing().asMilliseconds() + "ms");
            
            Vector2f mouse = Mouse.getPosition();
            x.add(mouse.x, x.getCurrentTime() + 1000);
            y.add(mouse.y, y.getCurrentTime() + 1000);
            
        });
        
        
        
        shape = new RectangleShape( 32.f, 32.f );
        shape.setPosition(100.f, 100.f);
        shape.setFillColor(new Color(255, 0, 0, 128));
        shape.setOutlineColor(Color.BLACK);
        shape.setOutlineThickness(2.f);
        shape.render(getRenderer());
        
        shapePast = new RectangleShape( 320.f, 320.f );
        shapePast.setPosition(100.f, 100.f);
        shapePast.setFillColor(new Color(0, 255, 0, 128));
        shapePast.setOutlineColor(Color.BLACK);
        shapePast.setOutlineThickness(2.f);
        shapePast.render(getRenderer());
        
        shapeFuture = new RectangleShape( 320.f, 320.f );
        shapeFuture.setPosition(100.f, 100.f);
        //shapeFuture.setFillColor(new Color(0, 0, 255, 128));
        shapeFuture.setOutlineColor(Color.BLACK);
        shapeFuture.setOutlineThickness(2.f);
        shapeFuture.render(getRenderer());
        
        x = new StateValue( 100.f );
        y = new StateValue( 100.f );
        
        getWindow().setBackgroundColor(Color.BLACK);
        
        font = new Font();
        //font.loadFromSystem("Times New Roman", java.awt.Font.BOLD, 12);
        font.loadFromFile("com/dinasgames/main/resources/arial.ttf");
//        
//        if(!font.loadFromSystem("Times New Roman", java.awt.Font.BOLD ,12 )) {
//          System.out.println("Failed to load font.");
//        }
        
        shapeFuture.setTexture(new Texture(font.getFont(12, 0).getTexture()));
        
        text = new Text();
        text.setCharacterSize(12);
        text.setText("Hello World");
        text.setColor(Color.BLACK);
        text.setFont(font);
        text.setPosition(10, 10);
        
       // getRenderer().clear();
        
        text.render(getRenderer());
        
//        x.add( 400, 2000 );
//        x.add( 200, 6000 );
//        
//        y.add( 200, 2000 );
//        y.add( 300, 3000 );
        
        
    }
    
    @Override
    public void tick() {
        
        super.tick();
        
        Mouse.tick(null);
        
        int past    = Math.max( 0, x.getCurrentTime() - 1000 );
        int future  = x.getCurrentTime() + 1000;
        
        shapePast.setPosition( x.getValue(past), y.getValue(past) );
        shapeFuture.setPosition( x.getValue(future), y.getValue(future) );
        shape.setPosition( x.getValue(), y.getValue() );
        
        
        if(Mouse.isButtonPressed(Mouse.Button.Left)) {
            Vector2f mouse = Mouse.getPosition();
            x.add(mouse.x, x.getCurrentTime() + 1000);
            y.add(mouse.y, y.getCurrentTime() + 1000);
            
        }
        
        //System.out.println(shape.getPosition());
        
        try {    
            server.update();
            client.update();
        } catch (Exception ex) {
            Logger.getLogger(NetGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void unload() {
        super.unload();
        
        client.disconnect();
        server.close();
        pingTimer.stop();

    }
    
}

