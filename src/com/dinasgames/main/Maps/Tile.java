package com.dinasgames.main.Maps;

import com.dinasgames.main.Math.Vector2f;
import java.awt.Color;

public class Tile {
  
    // An enumerator to describe the tile type
    enum Type {
        Nothing,
        Grassland,
        Water,
        Mountain
    }

    protected Type mType;
    protected final int mID;
    protected final Vector2f mPosition;

    public Tile(float x, float y, int id){
      mPosition = new Vector2f(x, y);
      mType = Type.Nothing;
      mID = id;
    }

    //Getters

    public int getID(){
      return mID;
    }

    public Type getTileType(){
      return mType;
    }

    public int getColor(){

      switch(mType){

        case Grassland:
          return new Color(38, 127, 0, 255).getRGB();
            
        case Water:
          return new Color(0,  74, 127).getRGB();
          
        case Mountain:
          return new Color(34, 34, 34, 255).getRGB();

      }

      return 0;
    }

    public Vector2f getPosition(){
      return mPosition;
    }


    //Setters

    public void setTileType(Type tileType){
      this.mType = tileType;
    }
  
  
  
}