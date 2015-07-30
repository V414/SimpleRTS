package com.dinasgames.main.maps;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.math.Vector2f;

public class Tile {
  
    // An enumerator to describe the tile type
    public enum Type {
        Nothing,
        Grassland,
        Water,
        Mountain
    }

    protected Type mType;
//    protected final int mID;
//    protected final Vector2f mPosition;

    public Tile( Type type ) {
      mType = type;
    }
    
//    public Tile(float x, float y, int id){
//      mPosition = new Vector2f(x, y);
//      mType = Type.Nothing;
//      mID = id;
//    }

    //Getters

//    public int getID(){
//      return mID;
//    }

    /**
     * Get the type of tile.
     * @return 
     */
    public Type getTileType(){
      return mType;
    }

    /**
     * Get the color that represents this tile.
     * @return 
     */
    public Color getColor(){

      switch(mType){

        case Grassland:
          return new Color(38, 127, 0, 255);
            
        case Water:
          return new Color(0,  74, 127);
          
        case Mountain:
          return new Color(34, 34, 34, 255);

      }

      return Color.BLACK;
    }

//    public Vector2f getPosition(){
//      return mPosition;
//    }
//
//
//    //Setters
//
//    public void setTileType(Type tileType){
//      this.mType = tileType;
//    }
  
  
  
}