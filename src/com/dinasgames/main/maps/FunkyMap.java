/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.maps;

import com.dinasgames.main.maps.tiles.Tile;
import com.dinasgames.engine.math.RandomNumber;
import com.dinasgames.main.maps.tiles.GrassTile;
import com.dinasgames.main.maps.tiles.MountainTile;
import com.dinasgames.main.maps.tiles.WaterTile;

/**
 *
 * @author Jack
 */
public class FunkyMap extends Map {

  @Override
  public int getPlayerCount() {
    return 4;
  }
  
  @Override
  public Tile[] generate() {
    
    // Create a blank map
    Tile[] tiles = newTiles();
    
    // Generate the map
    for(int x = 0; x < getWidthInTiles(); x++) {
      for(int y = 0; y < getHeightInTiles(); y++) {
        
        // Choose a tile at random
        Tile thisTile = null;
        
        switch( RandomNumber.choose(new Integer[]{ 0, 1, 2 }) ) {
          case 0: thisTile = new GrassTile(); break;
          case 1: thisTile = new WaterTile(); break;
          case 2: thisTile = new MountainTile(); break;
        }
        
        // Apply this tile
        setTile( tiles, x, y, thisTile );
        
      }
    }
    
    // Be sure to generate player positions
    for(int i = 0; i < getPlayerCount(); i++ ) {
      
      float x = i * 100.f;
      float y = i * 100.f;
      
      // Set the player start position
      setPlayerStart( i, x, y );
      
    }
    
    // Return the map
    return tiles;
    
  }
  
//    @Override
//    public void createMap() {
//        
//        super.createMap();
//        
//        int i = 0;
//        for(int x = 0; x < mMapSize.x; x++) {
//            for(int y = 0; y < mMapSize.y; y++) {
//                Tile t = new Tile(x * mTileSize, y * mTileSize, i++);
//                //t.setTileType(Tile.Type.Grassland);
//                t.setTileType(RandomNumber.choose(new Tile.Type[]{ Tile.Type.Grassland, Tile.Type.Water })); // << See RandomNumber.choose for more detail on what it does
//                mTileList.add(t);
//            }
//        }
//        
//        createChunks();
//        
//    }
    
}
