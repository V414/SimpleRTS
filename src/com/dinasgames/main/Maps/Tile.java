package com.dinasgames.main.Maps;

import com.dinasgames.main.Math.Vector2f;
import com.sun.prism.paint.Color;

public class Tile {
  
    // An enumerator to describe the tile type
    enum Type {
        Nothing,
        Grassland,
        Water
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
          return new Color(50, 200, 50, 255).getIntArgbPre();
            
        case Water:
            return Color.BLUE.getIntArgbPre();

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