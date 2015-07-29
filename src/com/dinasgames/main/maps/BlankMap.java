/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.maps;

import com.dinasgames.engine.math.RandomNumber;

/**
 *
 * @author Jack
 */
public class BlankMap extends Map {
    
    @Override
    public void createMap() {
        
        super.createMap();
        
        int i = 0;
        for(int x = 0; x < mMapSize.x; x++) {
            for(int y = 0; y < mMapSize.y; y++) {
                Tile t = new Tile(x * mTileSize, y * mTileSize, i++);
                //t.setTileType(Tile.Type.Grassland);
                t.setTileType(Tile.Type.Grassland); // << See RandomNumber.choose for more detail on what it does
                mTileList.add(t);
            }
        }
        
        createChunks();
        
    }
    
}
