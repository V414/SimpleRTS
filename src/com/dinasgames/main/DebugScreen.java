/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.Font;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.engine.graphics.Text;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jack
 */
public class DebugScreen {
  
  protected static Font font = Font.get("com/dinasgames/main/resources/arial.ttf");
  
  protected Text mText;
  protected List<DebugInfo> mInfo;
  
  public static class DebugInfo {
    
    public String name;
    public String value;
    
    public DebugInfo( String name, String value ) {
      this.name = name;
      this.value = value;
    }
    
  };
  
  public DebugScreen() {
    
    mInfo = new ArrayList();
    
    mText = new Text();
    mText.setCharacterSize(12);
    mText.setColor(Color.WHITE);
    mText.setFont(font);
    mText.setPosition(10.f, 10.f);
    mText.setGUI(true);
    
    mText.setDepth(-10000);
    
  }
  
  public DebugScreen( Renderer r ) {
    
    this();
    
    r.add(mText);
    
  }
  
  public void add( DebugInfo info ) {
    mInfo.add(info);
  }
  
  public void remove( DebugInfo info ) {
    mInfo.remove(info);
  }
  
  public void update() {
    
    String str = "Debug information: ";
    
    for(DebugInfo info : mInfo) {
      str += "\n" + info.name + ": " + info.value;
    }
    
    mText.setText(str);
    
  }
  
}
