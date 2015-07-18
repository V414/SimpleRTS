//Main Game File, includes the main GameLoop

package com.dinasgames.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.dinasgames.objects.*;
import java.awt.Shape;

public class Game extends JPanel{
  
  Riflemen rifle = new Riflemen();
  
  public Game(JFrame mainWindow){
    setBackground(new Color(50, 125, 0));
    
    
  }
  
  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    
    Graphics2D g2d = (Graphics2D) g;
    
    
    
  }
  
}