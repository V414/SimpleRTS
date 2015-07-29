/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.net;

import com.dinasgames.lwjgl.util.LWJGL;
import com.dinasgames.main.system.Clock;
import com.dinasgames.main.system.Time;
import java.awt.Font;
import org.newdawn.slick.TrueTypeFont;

//import org.newdawn.slick.Font;

/**
 *
 * @author Jack
 */
public class Main {
  
    public static void main(String args[]) {
      
        try {
            
            
          
            LWJGL.init();
            
            // Run Test Game
            NetGame test = new NetGame();
            
            
            
            test.run();
            
        } catch(RuntimeException e) {
            e.printStackTrace();
        } finally {
            LWJGL.free();
        }
        
    };
    
}
