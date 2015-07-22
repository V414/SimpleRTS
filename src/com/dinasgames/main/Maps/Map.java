package com.dinasgames.main.Maps;

import com.dinasgames.main.Math.Vector2f;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Map{
  
  private Vector2f mapSize;
  private final int tileSize;
  private List<Tile> tileList = new ArrayList<>();
  private List<BufferedImage> imageList = new ArrayList<>();
  
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
    
    createImages();
  }
  
  private void createImages(){
    for (Tile tile : tileList) {
      imageList.add(tile.getID(), new BufferedImage(
              tileSize, tileSize, BufferedImage.TYPE_INT_RGB));
      
      for(int y = 0; y<tileSize; y++){
        for(int x = 0; x<tileSize; x++){
          
          imageList.get(tile.getID()).setRGB(x, y, tile.getColor());
        }
      }
      
    }
  }
  
  
  //Getters
  
  public List<BufferedImage> getImageList(){
    return imageList;
  }
  
}