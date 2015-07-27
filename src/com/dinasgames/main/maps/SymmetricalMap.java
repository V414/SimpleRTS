package com.dinasgames.main.maps;

import com.dinasgames.main.math.Vector2f;

public class SymmetricalMap extends Map{
  
  public SymmetricalMap(){
    maxPlayers = 4;
    
    int mapWidth = (int) (mMapSize.x*mTileSize);
    int mapHeight = (int) (mMapSize.y*mTileSize);
    mPlayerStart.add(new Vector2f(mapWidth/4, mapHeight/4));
    mPlayerStart.add(new Vector2f(mapWidth/4, mapHeight - (mapHeight/4)));
    mPlayerStart.add(new Vector2f(mapWidth - (mapWidth/4), mapHeight/4));
    mPlayerStart.add(new Vector2f(mapWidth - (mapWidth/4), 
            mapHeight - (mapHeight/4)));
  }
    
  @Override
  public void createMap() {

    super.createMap();

    int i = 0;
    int mapBorderSize = 3;
    int mapWidth = (int) (mMapSize.x - 1);
    int mapHeight = (int) (mMapSize.y - 1);
    
    for(int x = 0; x < mMapSize.x; x++) {
      for(int y = 0; y < mMapSize.y; y++) {
        Tile t = new Tile(x * mTileSize, y * mTileSize, i++);
        if(y <= mapBorderSize || y >= mapHeight-mapBorderSize 
                || x <= mapBorderSize || x >= mapWidth-mapBorderSize){
          t.setTileType(Tile.Type.Mountain);
        }else{
        t.setTileType(Tile.Type.Grassland);
        }
        mTileList.add(t);
      }
    }
    
    i = 0;
    int centreMountainWidth = 6;
    
    for(int x = 0; x < mMapSize.x; x++) {
      for(int y = 0; y < mMapSize.y; y++) {
        if((x > mapWidth/2-centreMountainWidth/2 &&
                x < mapWidth/2+centreMountainWidth/2) &&
                (y < mapHeight/4 || y > mapHeight-(mapHeight/4))){
          mTileList.get(i).setTileType(Tile.Type.Mountain);
        }
        if((y > mapWidth/2-centreMountainWidth/2 &&
                y < mapWidth/2+centreMountainWidth/2) && 
                (x < mapHeight/4 || x > mapHeight-mapHeight/4)){
          mTileList.get(i).setTileType(Tile.Type.Mountain);
        }
        if((x > mapWidth/3 && x < mapWidth-(mapWidth/3)) &&
                (y > mapHeight/3 && y < mapHeight-(mapHeight/3))){
          mTileList.get(i).setTileType(Tile.Type.Water);
        }
        ++i;
      }
    }

    createChunks();

  }
    
  
}