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
      
      r.add(topBar);
    }
    
  }

  public void update() {
    
  }
  
}
