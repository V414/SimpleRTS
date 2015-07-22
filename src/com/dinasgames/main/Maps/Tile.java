package com.dinasgames.main.Maps;

import com.dinasgames.main.Math.Vector2f;
import com.sun.prism.paint.Color;

public class Tile{
  
  private String tileType;
  private final int mID;
  private Vector2f mPosition;
  
  public Tile(float x, float y, int id){
    mPosition = new Vector2f(x, y);
    this.mID = id;
  }
  
  //Getters
  
  public int getID(){
    return mID;
  }
  
  public String getTileType(){
    return tileType;
  }
  
  public int getColor(){
    Color color;
    switch(tileType){
      case "grassland":
        color = new Color(50, 200, 50, 255);
        return color.getIntArgbPre();
    }
    
    return 0;
  }
  
  public Vector2f getPosition(){
    return mPosition;
  }
  
  
  //Setters
  
  public void setTileType(String tileType){
    this.tileType = tileType;
  }
  
  
  
}