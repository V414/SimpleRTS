//Main Game File, includes the main GameLoop

package com.dinasgames.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.dinasgames.objects.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game extends JPanel{
  
  private final List<GameObject> objects = Collections.synchronizedList(new ArrayList<>());
  private boolean isGamePaused = true;
  
  public Game(JFrame mainWindow){
    setBackground(new Color(50, 125, 0));
    
    objects.add(0, new Rifleman());
  }
  
  private void doGameUpdates(double delta){
    
  }
  
  private void render(){
    validate();
    repaint();
  }
  
  private void gameLoop(){
    Thread gameLoopThread = new Thread(){

    @Override
    public void run() {
      long lastLoopTime = System.nanoTime();
      final int TARGET_FPS = 60;
      final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; 
      long lastFpsTime = 0;
      int fps = 60;

      while (isGamePaused == false){
        long now = System.nanoTime();
        long updateLength = now - lastLoopTime;
        lastLoopTime = now;
        double delta = updateLength / ((double)OPTIMAL_TIME);

        lastFpsTime += updateLength;
        fps++;

        if (lastFpsTime >= 1000000000){
          System.out.println("(FPS: "+fps+")");
          lastFpsTime = 0;
          fps = 0;
        }

        doGameUpdates(delta);
        render();

        long sleepTime = (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000;
        if(sleepTime <= 0){sleepTime = 1;}

        try{Thread.sleep(sleepTime);} 
        catch (InterruptedException ex) {System.out.println("Unable to sleep");}
      }
    }
    };

    gameLoopThread.start();
  }
  
  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    
    Graphics2D g2d = (Graphics2D) g;
    
    
    
  }
  
}