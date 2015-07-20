//Our Main Method - should be quite empty for now

package com.dinasgames.main;

import com.dinasgames.main.Scenes.TestScene;

public class Main {
    
    public static void main(String args[]) {
        
        Game.current = new Game();
        
        // Goto the test scene
        Game.current.setScene(new TestScene());
        
        while(Game.current.isRunning()) {
            
            Game.current.tick();
            
        }
        
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