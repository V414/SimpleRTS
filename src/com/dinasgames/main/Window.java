//Window class create our window using swing

package com.dinasgames.main;

import javax.swing.JFrame;

public class Window{
  
  public Window(){
    JFrame mainWindow = new JFrame();
    
    mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
    mainWindow.setVisible(false);
    mainWindow.setTitle("SimpleRTS");
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    mainWindow.setContentPane(new com.dinasgames.game.Game(mainWindow));
    mainWindow.setVisible(true);
  }
  
}