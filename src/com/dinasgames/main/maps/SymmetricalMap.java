package com.dinasgames.main.maps;

import com.dinasgames.engine.graphics.View;



public class SymmetricalMap extends Map {
  
  @Override
  public int getPlayerCount() {
    return 4;
  }
  
  @Override
  public Tile[] generate() {
    
    // Create a blank map
    Tile[] tiles = newTiles();
    
    // Generate the map    
    int mapWidth, mapHeight, mapBorderSize, centreMountainWidth;
    View centreMountain;
    
    mapWidth            = getWidthInTiles() - 1;
    mapHeight           = getHeightInTiles() - 1;
    mapBorderSize       = 3;
    centreMountainWidth = 6;
    centreMountain      = new View();
    centreMountain.setCenter( mapWidth / 2, mapHeight / 2 );
    centreMountain.setSize( mapWidth / 3, mapHeight / 3);
    
    for(int x = 0; x < getWidthInTiles(); x++) {
      for(int y = 0; y < getHeightInTiles(); y++) {
        
        if(
                
                /* Border */
                (
                      y <= mapBorderSize ||y >= mapHeight - mapBorderSize
                  ||  x <= mapBorderSize || x >= mapWidth - mapBorderSize
                )

                /* Or */
                ||

                /* Vertical bars */
              ( 
                    x > centreMountain.getCenter().x - centreMountainWidth / 2.f
                &&  x < centreMountain.getCenter().x + centreMountainWidth / 2.f
                &&  (y < (centreMountain.getCenter().y * 0.5f) || y > (centreMountain.getCenter().y * 1.5f) )
              )
                
                /* Or */
                ||
                
              /* Horizontal bars */
              ( 
                    y > centreMountain.getCenter().y - centreMountainWidth / 2.f
                &&  y < centreMountain.getCenter().y + centreMountainWidth / 2.f
                &&  (x < (centreMountain.getCenter().x * 0.5f) || x > (centreMountain.getCenter().x * 1.5f) )
              )
                
          )
                      
        {
          
          // Change this tile to mountain
          setTile( tiles, x, y, new Tile( Tile.Type.Mountain ));
          
        }else if(centreMountain.contains(x, y)) {
          
          // Change this tile to water
          setTile( tiles, x, y, new Tile( Tile.Type.Water ));
          
        }else{
          
          // Change this tile to grass
          setTile( tiles, x, y, new Tile( Tile.Type.Grassland ) );
          
        }
        
      }
    }
    
    // Be sure to generate player positions    
    
    // Convert to pixels instead of tiles
    mapWidth = getWidth();
    mapHeight = getHeight();
    
    setPlayerStart( 0, mapWidth/4, mapHeight/4 );
    setPlayerStart( 1, mapWidth/4, mapHeight - (mapHeight/4) );
    setPlayerStart( 2, mapWidth - (mapWidth/4), mapHeight/4 );
    setPlayerStart( 3, mapWidth - (mapWidth/4), mapHeight - (mapHeight/4) );
    
    // Return the map
    return tiles;
    
  }
  
//  public SymmetricalMap(){
//    maxPlayers = 4;
//    
//    int mapWidth = (int) (mMapSize.x*mTileSize);
//    int mapHeight = (int) (mMapSize.y*mTileSize);
//    mPlayerStart.add(new Vector2f(mapWidth/4, mapHeight/4));
//    mPlayerStart.add(new Vector2f(mapWidth/4, mapHeight - (mapHeight/4)));
//    mPlayerStart.add(new Vector2f(mapWidth - (mapWidth/4), mapHeight/4));
//    mPlayerStart.add(new Vector2f(mapWidth - (mapWidth/4), 
//            mapHeight - (mapHeight/4)));
//  }
//    
//  @Override
//  public void createMap() {
//
//    super.createMap();
//
//    int i = 0;
//    int mapBorderSize = 3;
//    int mapWidth = (int) (mMapSize.x - 1);
//    int mapHeight = (int) (mMapSize.y - 1);
//    
//    for(int x = 0; x < mMapSize.x; x++) {
//      for(int y = 0; y < mMapSize.y; y++) {
//        Tile t = new Tile(x * mTileSize, y * mTileSize, i++);
//        if(y <= mapBorderSize || y >= mapHeight-mapBorderSize 
//                || x <= mapBorderSize || x >= mapWidth-mapBorderSize){
//          t.setTileType(Tile.Type.Mountain);
//        }else{
//        t.setTileType(Tile.Type.Grassland);
//        }
//        mTileList.add(t);
//      }
//    }
//    
//    i = 0;
//    int centreMountainWidth = 6;
//    
//    for(int x = 0; x < mMapSize.x; x++) {
//      for(int y = 0; y < mMapSize.y; y++) {
//        if((x > mapWidth/2-centreMountainWidth/2 &&
//                x < mapWidth/2+centreMountainWidth/2) &&
//                (y < mapHeight/4 || y > mapHeight-(mapHeight/4))){
//          mTileList.get(i).setTileType(Tile.Type.Mountain);
//        }
//        if((y > mapWidth/2-centreMountainWidth/2 &&
//                y < mapWidth/2+centreMountainWidth/2) && 
//                (x < mapHeight/4 || x > mapHeight-mapHeight/4)){
//          mTileList.get(i).setTileType(Tile.Type.Mountain);
//        }
//        if((x > mapWidth/3 && x < mapWidth-(mapWidth/3)) &&
//                (y > mapHeight/3 && y < mapHeight-(mapHeight/3))){
//          mTileList.get(i).setTileType(Tile.Type.Water);
//        }
//        ++i;
//      }
//    }
//
//    createChunks();
//
//  }
//    
  
}