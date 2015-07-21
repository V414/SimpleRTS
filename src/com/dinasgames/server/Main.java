package com.dinasgames.server;

import com.dinasgames.server.net.NonBlockingClient;
import com.dinasgames.server.net.NonBlockingServer;
import com.dinasgames.main.Games.Game;
import com.dinasgames.main.System.Clock;
import com.dinasgames.server.net.Buffer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    
    public static void main(String args[]) {

            Clock clock = new Clock();
            Clock clock2 = new Clock();
            
            NonBlockingServer s = new NonBlockingServer();
            s.listen(12000);
            
            NonBlockingClient c = new NonBlockingClient();
            c.connect("127.0.0.1", 12000);
            
            while(true) {
                
                s.update();
                c.update();
                
                if(clock.getElapsedTime().asSeconds() >= 1.f) {
                    System.out.println("Server alive!");
                    clock.restart();
                }
                
                if(clock2.getElapsedTime().asSeconds() >= 10.f && c.isConnected()) {
                    System.out.println("Disconnecting client...");
                    c.disconnect();
                }
                
                if(clock2.getElapsedTime().asSeconds() >= 15.f) {
                    System.out.println("Shutting down server...");
                    s.close();
                    break;
                }
                
            }
            
           
        
    };
    
}
