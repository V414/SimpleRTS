//Our Main Method - should be quite empty for now

package com.dinasgames.main;

import com.dinasgames.main.Games.Game;
import com.dinasgames.main.Games.TestGame;

public class Main {
    
    public static void main(String args[]) {
        
        //try {
            
            // Create a new game instance
            Game.current = new TestGame();

            // Run the game
            Game.current.run();
        
//        } catch(Exception e) {
//            System.out.println("Exception: " + e.toString());
//        } finally {
//            Game.current = null;
//        }
        
    };
    
}

//import javax.swing.SwingUtilities;
//
//public class Main{
//  
//  public Main(){
//    
//  }
//  public static void main(String args[]){
//    SwingUtilities.invokeLater(new Runnable(){
//      @Override
//      public void run(){
//        Window window = new Window();
//      }
//    });
//  }
//  
//}