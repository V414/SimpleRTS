package com.dinasgames.main.Maps;

import com.dinasgames.main.Math.Vector2f;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Map{
  
  private Vector2f mapSize;
  private final int tileSize;
  private List<Tile> tileList = new ArrayList<>();
  private List<BufferedImage> chunkList = new ArrayList<>();
  
  public Map(){
    mapSize = new Vector2f(100, 100);
    tileSize = 10;
  }
  
  //Generate Map
  public void createMap(){
    int i = 0;
    
    for(int y = 0; y<mapSize.y; y++){
      for(int x = 0; x<mapSize.x; x++){
        tileList.add(new Tile(x*tileSize, y*tileSize, i));
        tileList.get(i).setTileType("grassland");
        ++i;
      }
    }
    
    createChunks();
  }
  
  private void createChunks(){
    BufferedImage newImage = new BufferedImage(
              256, 256, BufferedImage.TYPE_INT_RGB);
    
    for (int mY = 0; mY < mapSize.y; mY++){
      for (int mX = 0; mX < mapSize.x; mX++){
        for (Tile tile : tileList) {

          for(int y = 0; y<tileSize; y++){
            for(int x = 0; x<tileSize; x++){
              newImage.setRGB(
                      (int) (x+tile.getPosition().x), 
                      (int) (y+tile.getPosition().y), 
                       tile.getColor());
              
              chunkList.add(newImage);
            }
          }
        }
      }
    }
    
  }
  
  
  //Getters
  
  public List<BufferedImage> getChunkList(){
    return chunkList;
  }
  
}