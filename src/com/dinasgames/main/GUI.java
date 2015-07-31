package com.dinasgames.main;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.Font;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.engine.graphics.Text;
import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.main.games.WindowGame;
import com.dinasgames.main.scenes.Scene;

public class GUI {
  
  protected static Font font = Font.get("com/dinasgames/main/resources/arial.ttf");
  
  protected RectangleShape topBar;
  protected RectangleShape topBarCenter;
  
  protected RectangleShape bottomBar;
  protected RectangleShape minimap;
  
  
  protected Scene mScene;
  protected float mWindowWidth;
  protected float mWindowHeight;
  
  public GUI(Renderer r, Scene scene) {
    
    mScene = scene;

    if(mScene.getGame() instanceof WindowGame){
      WindowGame windowGame = (WindowGame) mScene.getGame();
      mWindowWidth = windowGame.getWindow().getWidth();
      mWindowHeight = windowGame.getWindow().getHeight();
      
      topBar = new RectangleShape(mWindowWidth, 40);
      topBar.setPosition(0, 0);
      topBar.setFillColor(new Color(150, 200, 200));
      topBar.setOutlineColor(Color.BLACK());
      topBar.setOutlineThickness(2.f);
      topBar.setOrigin(0, 0);
      topBar.setDepth(-999);
      topBar.setGUI(true);
      
      topBarCenter = new RectangleShape(200, 70);
      topBarCenter.setPosition(mWindowWidth/2-100, 0);
      topBarCenter.setFillColor(new Color(150, 200, 200));
      topBarCenter.setOutlineColor(Color.BLACK());
      topBarCenter.setOutlineThickness(2.f);
      topBarCenter.setOrigin(0, 0);
      topBarCenter.setDepth(-999);
      topBarCenter.setGUI(true);
      
      bottomBar = new RectangleShape(mWindowWidth, 30);
      bottomBar.setPosition(0, mWindowHeight-bottomBar.getHeight());
      bottomBar.setFillColor(new Color(150, 200, 200));
      bottomBar.setOutlineColor(Color.BLACK());
      bottomBar.setOutlineThickness(2.f);
      bottomBar.setOrigin(0, 0);
      bottomBar.setDepth(-999);
      bottomBar.setGUI(true);
      
      minimap = new RectangleShape(200, 200);
      minimap.setPosition(mWindowWidth - minimap.getWidth(), mWindowHeight-minimap.getHeight()-bottomBar.getHeight()-2);
      minimap.setFillColor(new Color(150, 200, 200));
      minimap.setOutlineColor(Color.BLACK());
      minimap.setOutlineThickness(2.f);
      minimap.setOrigin(0, 0);
      minimap.setDepth(-999);
      minimap.setGUI(true);
      
      r.add(bottomBar);
      r.add(topBar);
      r.add(topBarCenter);
      r.add(minimap);
    }
    
  }

  public void update() {
    
  }
  
}
