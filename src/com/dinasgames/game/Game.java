//Main Game File, includes the main GameLoop

package com.dinasgames.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.dinasgames.objects.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.SwingUtilities;

public class Game extends JPanel{
  
  private final List<GameObject> objects = 
          Collections.synchronizedList(new ArrayList<>());
  private boolean isGamePaused = true;
  private final int mapWidth = 10000;
  private final int mapHeight = 10000;
  private int cameraX = 0;
  private int cameraY = 0;
  private final int screenWidth;
  private final int screenHeight;
  private boolean moveCameraUp = false;
  private boolean moveCameraDown = false;
  private boolean moveCameraLeft = false;
  private boolean moveCameraRight = false;
  private boolean selectingUsingRectangle = false;
  private int rectStartX;
  private int rectStartY;
  private int rectEndX;
  private int rectEndY;
  private Rectangle selectionRect = new Rectangle();
  
  public Game(JFrame mainWindow){
    GraphicsDevice gd = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getDefaultScreenDevice();
    screenWidth = gd.getDisplayMode().getWidth();
    screenHeight = gd.getDisplayMode().getHeight();
    setBackground(new Color(50, 125, 0));
    
    objects.add(0, new Rifleman(300, 300, 0, 0));
    
    objects.add(1, new LightTank(400, 400, 0, 0));
    
    addMouseMotionListener(new MouseMotionListener(){

      @Override
      public void mouseDragged(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
          if(selectingUsingRectangle == true){
            rectEndX = e.getX();
            rectEndY = e.getY();
          }
        }
      }

      @Override
      public void mouseMoved(MouseEvent e) {
        moveCamera(e);
      }
      
    });
    addMouseListener(new MouseListener(){

      @Override
      public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
          synchronized (objects){
          for (GameObject gameObject : objects) {
            Unit unit = (Unit) gameObject;
            unit.setIsSelected(false);
            if((e.getX() > unit.getX()-cameraX && 
                    e.getX() < unit.getX()-cameraX+unit.getObjectWidth()) &&
                (e.getY() > unit.getY()-cameraY && 
                    e.getY() < unit.getY()-cameraY+unit.getObjectHeight())){

              unit.setIsSelected(true);
            }
          }
          }
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
          selectingUsingRectangle = true;
          rectStartX = e.getX();
          rectStartY = e.getY();
          rectEndX = e.getX();
          rectEndY = e.getY();
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
          selectingUsingRectangle = false;
          isInSelectionRectangle();
        }
      }

      @Override
      public void mouseEntered(MouseEvent e) {}

      @Override
      public void mouseExited(MouseEvent e) {}
      
    });
    
    isGamePaused = false;
    gameLoop();
  }
  
  private void moveCamera(MouseEvent e){
    if(e.getX() < 200){
      moveCameraLeft = true; moveCameraRight = false;
    }else{
      moveCameraLeft = false;
    }
    
    if(e.getX() > screenWidth - 200){
      moveCameraRight = true; moveCameraLeft = false;
    }else{
      moveCameraRight = false;
    }
    
    if(e.getY() < 200){
      moveCameraUp = true; moveCameraDown = false;
    }else{
      moveCameraUp = false;
    }
    
    if(e.getY() > screenHeight - 200){
      moveCameraDown = true; moveCameraUp = false;
    }else{
      moveCameraDown = false;
    }
  }
  
  private void isInSelectionRectangle(){
    synchronized (objects){
    for (GameObject gameObject : objects) {
      Unit unit = (Unit) gameObject;
      unit.setIsSelected(false);

      Rectangle unitRect = new Rectangle();
      unitRect.setBounds((int) unit.getX()-cameraX, (int) unit.getY()-cameraY, 
              unit.getObjectWidth(), unit.getObjectHeight());

      if(selectionRect.intersects(unitRect)){
        unit.setIsSelected(true);
      }
    }
    }
  }
  
  private void doGameUpdates(double delta){
    if(moveCameraLeft == true && cameraX > 0){ cameraX = cameraX - 5; }
    if(moveCameraRight == true && cameraX < mapWidth){ cameraX = cameraX + 5; }
    if(moveCameraUp == true && cameraY > 0){ cameraY = cameraY - 5; }
    if(moveCameraDown == true && cameraY < mapHeight){ cameraY = cameraY + 5; }
    
    synchronized (objects){
      for (GameObject gameObject : objects) {
        gameObject.onTick(delta, cameraX, cameraY);
      }
    }
    
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
    
    synchronized (objects){
      for (GameObject gameObject : objects) {
        //Importing shapes, method of drawing shape, and colour
        DrawShapes[] drawShapes = gameObject.getDrawShapes();
        
        //Looping through shapes to draw
        for (int i = 0; i < drawShapes.length; i++) {
          //Setting Colour
          g2d.setColor(drawShapes[i].getColor());
          
          //Setting old transformation, so that we can reset the angle
          AffineTransform oldRotation = g2d.getTransform();
          g2d.rotate(Math.toRadians(gameObject.getAngle()), 
                  gameObject.getOriginX()-cameraX, 
                  gameObject.getOriginY()-cameraY);
          
          //Choosing Draw Type
          if(drawShapes[i].drawType().equals("draw")){
            g2d.draw(drawShapes[i].getShape());
          }else if(drawShapes[i].drawType().equals("fill")){
            g2d.fill(drawShapes[i].getShape());
          }
          
          //Resetting to old transformation
          g2d.setTransform(oldRotation);
        }
      }
      
      for (GameObject gameObject : objects) {
        Unit unit = (Unit) gameObject;
        
        if(unit.getIsSelected() == true){
          DrawShapes[] statusBars = unit.getStatusBars();

          for (int i = 0; i < statusBars.length; i++) {
            //Setting Colour
            g2d.setColor(statusBars[i].getColor());

            //Choosing Draw Type
            if(statusBars[i].drawType().equals("draw")){
              g2d.draw(statusBars[i].getShape());
            }else if(statusBars[i].drawType().equals("fill")){
              g2d.fill(statusBars[i].getShape());
            }
          }
        }
      }
      
      if(selectingUsingRectangle == true){
        g2d.setColor(Color.red);
        selectionRect.setFrameFromDiagonal(rectStartX, rectStartY, rectEndX, rectEndY);
        g2d.draw(selectionRect);
      }
    }
    
  }
  
}