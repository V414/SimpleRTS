package com.dinasgames.main.Maps;

public class SymmetricalMap extends Map{
  
  public SymmetricalMap(){
    
  }
    
  @Override
  public void createMap() {

    super.createMap();

    int i = 0;
    for(int x = 0; x < mMapSize.x; x++) {
      for(int y = 0; y < mMapSize.y; y++) {
        Tile t = new Tile(x * mTileSize, y * mTileSize, i++);
        t.setTileType(Tile.Type.Grassland);
        mTileList.add(t);
      }
    }

    createChunks();

  }
    
  
}