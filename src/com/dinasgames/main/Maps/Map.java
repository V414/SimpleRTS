package com.dinasgames.main.Maps;

import com.dinasgames.main.Math.Vector2f;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Map {
  
    public class MapChunk {
        
        public int mChunkX, mChunkY;
        public BufferedImage mImage;
        
        public MapChunk(BufferedImage img, int cx, int cy) {
            mChunkX = cx;
            mChunkY = cy;
            mImage = img;
        }
        
    };
    
    protected Vector2f mMapSize;
    protected final int mChunkSize;
    protected final int mTileSize;
    protected List<Tile> mTileList;
    protected List<MapChunk> mChunkList;
    protected int maxPlayers;
    protected Vector2f[] playerStart;
    
    
    public Map() {
        mMapSize = new Vector2f(128, 128);  // << Map is 100x100 tiles
        mTileSize = 8;                     // << Each tile is 10x10 pixels
                                            // << 100x10 = 1000 pixels (actual size of map image)
        mChunkSize = 256;                   // << Chunk size in pixels
        maxPlayers = 1;
        playerStart = null;
    }

    /**
     * Generate a list of tiles that will be drawn
     */
    public void createMap() {
        
        // Clear and create a new map
        mTileList = new ArrayList();
        mChunkList = new ArrayList();
        
    }
    
    /**
     * Get the buffer for pixel point (x,y). Will generate one if it doesn't exist.
     * @param x
     * @param y
     * @return BufferedImage
     */
    protected MapChunk get(int x, int y) {
        
        // Figure out which chunk this is in
        int cx = 0, cy = 0;
        while(x >= mChunkSize) {
            cx ++;
            x -= mChunkSize;
        }
        while(y >= mChunkSize) {
            cy ++;
            y -= mChunkSize;
        }
        
        // Check to see if this buffer already exists
        for(MapChunk chunk : mChunkList) {
            if(chunk.mChunkX == cx && chunk.mChunkY == cy) {
                return chunk;
            }
        }
        
        // This chunk hasn't been made yet. So make it
        BufferedImage image = new BufferedImage(mChunkSize, mChunkSize, 
                                                BufferedImage.TYPE_INT_RGB);
        MapChunk chunk = new MapChunk(image, cx, cy);
        mChunkList.add(chunk);
        
        return chunk;
        
    }
    
    /**
     * Get chunk size in pixels.
     * @return 
     */
    public int getChunkSize() {
        return mChunkSize;
    }
    
    public int getMaxPlayers() {
      return maxPlayers;
    }
    
    public Vector2f[] getPlayerStart(){
      return playerStart;
    }
    
    /**
     * Get map width in chunks.
     * @return 
     */
    public int getWidth() {
        return (int)mMapSize.x;
    }
    
    /**
     * Get map height in chunks.
     * @return 
     */
    public int getHeight() {
        return (int)mMapSize.y;
    }
    
    public MapChunk getChunkAt(int x, int y) {
        for(MapChunk chunk : mChunkList) {
            if(chunk.mChunkX == x && chunk.mChunkY == y) {
                return chunk;
            }
        }
        return null;
    }
    
    /**
     * Generate a series of Buffered images which draw the generated tiles from mTileList.
     */
    protected void createChunks() {
        
        // NOTE: Each BufferedImage will be 256x256
        
        // Loop through the tiles
        for(Tile tile : mTileList) {
            
            int tx, ty;
            tx = (int)tile.getPosition().x;
            ty = (int)tile.getPosition().y;
            
            for(int x = tx; x < tx + mTileSize; x++) {
                for(int y = ty; y < ty + mTileSize; y++) {
                    
                    
                    
                    MapChunk thisChunk = get(x, y);
                    
                    //System.out.println((x - (thisChunk.mChunkX*mChunkSize)) + "," + (y - (thisChunk.mChunkY*mChunkSize)));
                    
                    thisChunk.mImage.setRGB(x - (thisChunk.mChunkX*mChunkSize),
                                            y - (thisChunk.mChunkY*mChunkSize),
                                            tile.getColor());
                    
                }
            }
            
        }
        
    }
  
  //Generate Map
//  public void createMap(){
//      
//    int i = 0;
//    for(int y = 0; y<mMapSize.y; y++){
//      for(int x = 0; x<mMapSize.x; x++){
//        Tile tile = new Tile(x*mTileSize, y*mTileSize, i++);
//        tile.setTileType(Tile.Type.Grassland);
//        mTileList.add(tile);
//      }
//    }
//    
//    createChunks();
//  }
  
//  private void createChunks(){
//    
//    
//    for (int mY = 0; mY < mMapSize.y; mY++){
//        for (int mX = 0; mX < mMapSize.x; mX++){
//            BufferedImage newImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
//            for (Tile tile : mTileList) {
//                for(int y = 0; y<mTileSize; y++){
//                  for(int x = 0; x<mTileSize; x++){
//                    newImage.setRGB(
//                            (int) (x+tile.getPosition().x), 
//                            (int) (y+tile.getPosition().y), 
//                             tile.getColor());
//                    mChunkList.add(newImage);
//                  }
//                }
//            }
//        }
//    }
//    
//  }
  
  
  //Getters
  
  public List<MapChunk> getChunkList(){
    return mChunkList;
  }
  
}
