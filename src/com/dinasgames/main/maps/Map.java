package com.dinasgames.main.maps;

import com.dinasgames.main.maps.tiles.Tile;
import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.Image;
import com.dinasgames.engine.graphics.Renderable;
import com.dinasgames.engine.graphics.Texture;
import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.engine.math.Vector2i;
import com.dinasgames.engine.pathfinding.Mover;
import com.dinasgames.engine.pathfinding.TileBasedMap;
import com.dinasgames.main.maps.tiles.PathTile;
import java.util.ArrayList;
import java.util.List;

public class Map extends Renderable implements TileBasedMap {

  /**
   * A data structure to store map chunk data.
   */
  public class MapChunk {

      public Image image;
    
      public MapChunk( int width, int height ) {
        image = new Image();
        image.create(width, height, Color.BLACK);
      }
      
//      public int mChunkX, mChunkY;
//      public Image mImage;
//
//      public MapChunk(Image img, int cx, int cy) {
//          mChunkX = cx;
//          mChunkY = cy;
//          mImage = img;
//      }

  };
  
  /**
   * A class to represent a player start location.
   */
  public class PlayerStart {
    
    public float x, y;
    
    public PlayerStart( float x, float y ) {
      this.x = x;
      this.y = y;
    }
    
  }
  
  /**
   * A list of Path finding tiles used to check whether certain units can pass parts of the map.
   */
  private PathTile[] mPathTiles;
  
  /**
   * A list of starting positions for players.
   */
  private PlayerStart[] mPlayerStartList;
  
  /**
   * Keep track of textures for rendering
   */
  private Texture[] mTextures;
  
  /**
   * Keep track of render chunks.
   */
  private RectangleShape[] mShapes;
  
  /**
   * Default constructor
   */
  public Map() {
    
  }
  
  /**
   * Get the number of players this map supports.
   * @return 
   */
  public int getPlayerCount() {
    return 0;
  }
  
  /**
   * Get the list of player starting positions. Note: This function will run the generate function if player information hasn't already been calculated.
   * @return 
   */
  public PlayerStart[] getPlayerStartingPoints() {
    if(mPlayerStartList == null) {
      generate();
    }
    return mPlayerStartList;
  }
  
  /**
   * Function to calculate the path finding grid using the tiles given.
   * @param tiles
   */
  protected void calculatePathfindingGrid(Tile[] tiles) {
    
    if(tiles == null) {
      return;
    }
    
    /**
     * Problem:
     * Because the tile size used by the path finding engine will most likely be
     * different from that of the visual effect size, we have to
     * look at all the visual tiles and judge whether they are blocking of
     * particular kinds of unit.
     * 
     * Algorithm:
     * For land, air and sea take a look at the number of blocking/non-blocking
     * tiles there are and if there is over 50% either way then that will be the
     * PathTiles result.
     */
    
    // First get the size of the map in PathTile(s)
    int rows = getActualHeightInTiles();
    int cols = getActualWidthInTiles();
    
    // Calculate the number of tiles to a PathTile
    int tilesPerActualTilesX = getActualTileWidth() / getTileWidth();
    int tilesPerActualTilesY = getActualTileHeight() / getTileHeight();
    int tileCount = tilesPerActualTilesX * tilesPerActualTilesY;
    
    int tileCols = getWidthInTiles();
    
    // Create an array
    mPathTiles = new PathTile[ rows * cols ];
    
    // Iterate through these tiles
    for(int x = 0; x < cols ; x++) {
      for(int y = 0; y < rows; y++) {
        
        // Setup some variables
        int blockSea, blockAir, blockLand;
        blockAir = blockLand = blockSea = 0;
        
        // Figure out the percentage of each block
        for(int tx = 0; tx < tilesPerActualTilesX; tx++) {
          for(int ty = 0; ty < tilesPerActualTilesY; ty++) {
            
            int tileX = (x * tilesPerActualTilesX) + tx;
            int tileY = (y * tilesPerActualTilesY) + ty;
            
            Tile thisTile = tiles[ (tileY * tileCols) + tileX ];
            
            if(thisTile.blockAir()) {
              blockAir++;
            }
            
            if(thisTile.blockLand()) {
              blockLand++;
            }
            
            if(thisTile.blockSea()) {
              blockSea++;
            }
            
          }
        }
        
        // Calculate percentage
        float a,b,c;
        a = (float)blockAir / (float)tileCount;
        b = (float)blockLand / (float)tileCount;
        c = (float)blockSea / (float)tileCount;
        
        // Set the new tile
        mPathTiles[ (y * cols) + x ] = new PathTile( (a >= .5f), (b >= .5f), (c >= .5f) );
        
      }
    }
    
  }
  
  /**
   * Replace a tile at position x,y in tiles array.
   * @param tiles
   * @param x
   * @param y
   * @param tile 
   */
  protected void setTile( Tile[] tiles, int x, int y, Tile tile ) {
    tiles[ (y * getHeightInTiles()) + x ] = tile;
  }
  
  /**
   * Get a specific tile from position x,y within an array.
   * @param tiles
   * @param x
   * @param y
   * @return 
   */
  protected Tile getTile( Tile[] tiles, int x, int y ) {
    return tiles[ (y * getHeightInTiles()) + x ];
  }
  
  /**
   * Generate a Tile array of the correct size and nullify it.
   * @return 
   */
  protected Tile[] newTiles() {
    int len = getWidthInTiles() * getHeightInTiles();
    Tile[] t = new Tile[ len ];
    for(int i = 0; i < len; i++) {
      t[i] = null;
    }
    return t;
  }
  
  /**
   * Get a list filled with player start positions.
   * @return 
   */
  public List<Vector2f> getPlayerStartPositions() {
    
    List<Vector2f> list = new ArrayList();
    PlayerStart[] points = getPlayerStartingPoints();
    
    if(points == null || points.length <= 0) {
      return list;
    }
    
    for(int i = 0; i < points.length; i++) {
      PlayerStart p = points[i];
      list.add(new Vector2f( p.x, p.y ));
    }
    
    return list;
    
  }
  
  /**
   * Set the start position for player idx.
   * @param idx
   * @param x
   * @param y 
   */
  protected void setPlayerStart( int idx, float x, float y ) {
    if(mPlayerStartList == null) {
      newPlayerList();
    }
    if(idx < 0 || idx >= mPlayerStartList.length) {
      return;
    }
    mPlayerStartList[idx].x = x;
    mPlayerStartList[idx].y = y;
  }
  
  /**
   * Generate a blank player list. This will set the start position for each player to 0,0
   */
  protected void newPlayerList() {
    int len = getPlayerCount();
    mPlayerStartList = new PlayerStart[ len ];
    for(int i = 0; i < len; i++) {
      mPlayerStartList[i] = new PlayerStart( 0.f, 0.f );
    }
  }
  
  /**
   * Generate the whole map including path finding tiles and player locations.
   * @return 
   */
  public Tile[] generate() {
    Tile[] tiles = generateTiles();
    calculatePathfindingGrid(tiles);
    return tiles;
  }
  
  /**
   * Generate the map tiles. Note: These tiles are used for rendering.
   * @return 
   */
  public Tile[] generateTiles() {
    return null;
  }
  
  /**
   * Render the map into a series of chunks.
   * @param tiles
   * @return 
   */
  protected MapChunk[] generateChunks( Tile[] tiles ) {
    
    // Check that the tiles we have been given are valid.
    if(tiles == null) {
      return null;
    }
    
    // Ensure we have tiles
    if(tiles.length <= 0) {
      return null;
    }
    
    // Ensure that the tile count is correct
    int tileRows = getHeightInTiles();
    int tileCols = getWidthInTiles();
    
    int tileCount = tileRows * tileCols;
    
    if(tiles.length != tileCount) {
      return null;
    }
    
    // Calculate the amount of chunks we need
    int chunkRows = getWidthInChunks();
    int chunkCols = getHeightInChunks();
    
    int chunkCount =  chunkRows * chunkCols;
    
    // Create an array to store these chunks
    MapChunk[] chunks = new MapChunk[ chunkCount ];
    
    // Nullify the chunks
    for( int i = 0; i < chunkCount; i++ ) {
      chunks[i] = null;
    }
    
    // Render the tiles onto the chunks
    for(int x = 0; x < tileCols; x++) {
      for(int y = 0; y < tileRows; y++) {
        
        // Get the current tile
        Tile thisTile = tiles[ (y * tileCols) + x ];
        
        // Figure out which chunk we're in        
        int tileX = x * getTileWidth();
        int tileY = y * getTileHeight();
        
        int chunkX = 0;
        int chunkY = 0;
        
        while( tileX >= getChunkWidth() ) {
          chunkX ++;
          tileX -= getChunkWidth();
        }
        
        while( tileY >= getChunkHeight() ) {
          chunkY ++;
          tileY -= getChunkHeight();
        }
        
        // Find the chunk
        MapChunk chunk = chunks[ (chunkY * chunkCols) + chunkX ];
        
        System.out.println(" Tile " + ((y * tileCols) + x) +  " (" + x + ", " + y + ") Tile Position " + (x * getTileWidth()) + ", " + (y * getTileHeight()) + "  is in Chunk " + chunkX + ", " + chunkY + " which is at index " + ((chunkY * chunkCols) + chunkX));
        
        // Ensure this chunk has been created
        if(chunk == null) {
          chunk = new MapChunk( getChunkWidth(), getChunkHeight() );
          chunks[ (chunkY * chunkCols) + chunkX ] = chunk;
          
        }
        
        // Draw the tile
        for(int tx = 0; tx < getTileWidth(); tx++ ){
          for(int ty = 0; ty < getTileHeight(); ty++) {
            chunk.image.setPixel(
                    
                    tx + (x * getTileWidth()) - (chunkX * getChunkWidth()),
                    ty + (y * getTileHeight()) - (chunkY * getChunkHeight()),
                    thisTile.getColor()
                    
            );
          }
        }
        
      }
    }
    
    return chunks;
    
  }
  
  /**
   * Get the size of the map in chunks.
   * @return 
   */
  public Vector2i getSizeInChunks() {
    return new Vector2i(getWidthInChunks(), getHeightInChunks());
  }
  
  /**
   * Get the height of the map in chunks.
   * @return 
   */
  public int getHeightInChunks() {
    return (int)Math.ceil((double)getHeight()  / (double)getChunkHeight());
  }
  
  /**
   * Get the width of the map in chunks.
   * @return 
   */
  public int getWidthInChunks() {
    return (int)Math.ceil((double)getWidth()  / (double)getChunkWidth());
  }
  
  /**
   * Get the width of the map in PathTile(s)
   * @return 
   */
  public int getActualWidthInTiles() {
    return getWidth() / getActualTileWidth();
  }
  
  /**
   * Get the height of the map in PathTile(s)
   * @return 
   */
  public int getActualHeightInTiles() {
    return getHeight() / getActualTileHeight();
  }
  
  /**
   * Get the size of the map in PathTile(s)
   * @return 
   */
  public Vector2i getActualSizeInTiles() {
    return new Vector2i(getActualWidthInTiles(), getActualHeightInTiles());
  }
  
  /**
   * Get the height of a single image chunk. Note: this is used for rendering.
   * @return 
   */
  public int getChunkHeight() {
    return 256;
  }
  
  /**
   * Get the width of a single image chunk. Note: this is used for rendering.
   * @return 
   */
  public int getChunkWidth() {
    return 256;
  }
  
  /**
   * Get the actual height of a tile. Note: this is used for the path finding grid.
   * @return 
   */
  public int getActualTileHeight() {
    return 32;
  }
  
  /**
   * Get the actual width of a tile. Note: this is used for the path finding grid.
   * @return 
   */
  public int getActualTileWidth() {
    return 32;
  }
  
  /**
   * Get the size of the map in tiles. Note: These tiles are used for rendering.
   * @return 
   */
  public Vector2i getSizeInTiles() {
    return new Vector2i(getWidthInTiles(), getHeightInTiles());
  }
  
  /**
   * Get the size of a chunk. Note: This chunk is used for rendering.
   * @return 
   */
  public Vector2i getChunkSize() {
    return new Vector2i(getChunkWidth(), getChunkHeight());
  }
  
  /**
   * Get the size of a single tile.
   * @return 
   */
  public Vector2i getTileSize() {
    return new Vector2i(getTileWidth(), getTileHeight());
  }
  
  /**
   * Get the size of the map in pixels.
   * @return 
   */
  public Vector2i getSize() {
    return new Vector2i(getWidth(), getHeight());
  }
  
  /**
   * Get the height of the map in pixels.
   * @return 
   */
  public int getHeight() {
    return getTileHeight() * getHeightInTiles();
  }
  
  /**
   * Get the width of the map in pixels.
   * @return 
   */
  public int getWidth() {
    return getTileWidth() * getWidthInTiles();
  }
  
  /**
   * Get the height of a single tile. Note: The is used for the visual representation of a tile.
   * @return 
   */
  public int getTileHeight() {
    return 8;
  }

  /**
   * Get the width of a single tile. Note: This is used for the visual representation of a tile.
   * @return 
   */
  public int getTileWidth() {
    return 8;
  }
  
  @Override
  /**
   * Get the width of the map in tiles.
   */
  public int getWidthInTiles() {
    return 128;
  }

  @Override
  /**
   * Get the height of the map in tiles.
   */
  public int getHeightInTiles() {
    return 128;
  }

  @Override
  public void pathFinderVisited(int x, int y) {
    // Ignore this event
  }

  @Override
  /**
   * Check whether a certain tile is blocked for a particular Mover.
   */
  public boolean blocked(Mover mover, int x, int y) {
    return false;
  }

  @Override
  /**
   * Check the cost of a path for a particular mover.
   */
  public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
    return 0.f;
  }
  
  @Override
  public void onAdd() {
    
    // Ensure we remove old data
    onRemove();
    
    // Generate a list of renderable images
    MapChunk[] chunks = generateChunks(generate());
    
    // Ensure the list is valid
    if(chunks == null) {
      return;
    }
    
    // Move the textures onto Rectangle shapes
    int chunkWidth = getChunkWidth();
    int chunkHeight = getChunkHeight();
    
    int rows = getHeightInChunks();
    int cols = getWidthInChunks();
    
    // Init our storage
    mTextures = new Texture[ rows * cols ];
    mShapes   = new RectangleShape[ rows * cols ];
    
    // Iterate the whole map in chunks
    for(int x = 0; x < cols; x++) {
      for(int y = 0; y < rows; y++) {
        
        // Get the chunk for this position
        MapChunk thisChunk = chunks[ (y * cols) + x ];
        
        if(thisChunk == null) {
          // Something went wrong. Undo.
          onRemove();
          return;
        }
        
        // Convert our image into a texture
        Texture t = new Texture();
        if(t == null || !t.loadFromImage(thisChunk.image)) {
          onRemove();
          return;
        }
        
        // Store the texture for removal later on
        mTextures[ (y * cols) + x ] = t;
        
        // Create a rectangle shape
        RectangleShape r = new RectangleShape( chunkWidth, chunkHeight );
        
        // Apply some settings
        r.setTexture(t);
        r.setPosition( x * chunkWidth, y * chunkHeight );
        
        // Add this shape to the renderer
        if(mRenderer.add(r) < 0) {
          onRemove();
          return;
        }
        
        // Store the shape
        mShapes[ (y * cols) + x ] = r;
        
      }
    }
    
  }
  
  @Override
  public void onRemove() {
    
    // Remove textures
    if(mTextures != null) {
      
      // Remove from graphics memory
      for(int i = 0; i < mTextures.length; i++) {
        mTextures[i].destruct();
      }
      
      // Remove from memory
      mTextures = null;
      
    }
    
    // Remove shapes
    if(mShapes != null) {
      
      // Remove from renderer
      for(int i = 0; i < mShapes.length; i++) {
        mShapes[i].remove();
      }
      
      // Remove from list
      mShapes = null;
      
    }
    
  }
  
  @Override
  public void onDepthChange(int oldValue, int newValue) {

    if(mShapes != null) {
      
      for(int i = 0; i < mShapes.length; i++) {
        mShapes[i].setDepth(newValue);
      }

    }
    
  }

  @Override
  public void onVisibilityChange(boolean oldValue, boolean newValue) {

    if(mShapes != null) {
      
      for(int i = 0; i < mShapes.length; i++) {
        mShapes[i].setVisible(newValue);
      }

    }
    
  }
  
  /**
   * Generate a Renderable GridShape to draw path grid.
   * @return 
   */
//  public GridShape getDebugGrid() {
//    
//  }
    
    
    
    
    
//    protected Vector2f mMapSize;
//    protected final int mChunkSize;
//    protected final int mTileSize;
//    protected List<Tile> mTileList;
//    protected List<MapChunk> mChunkList;
//    protected int maxPlayers;
//    protected List<Vector2f> mPlayerStart;
//    
//    
//    public Map() {
//        mMapSize = new Vector2f(128, 128);  // << Map is 100x100 tiles
//        mTileSize = 8;                     // << Each tile is 10x10 pixels
//                                            // << 100x10 = 1000 pixels (actual size of map image)
//        mChunkSize = 256;                   // << Chunk size in pixels
//        maxPlayers = 1;
//        mPlayerStart = new ArrayList();
//    }
//
//    /**
//     * Generate a list of tiles that will be drawn
//     */
//    public void createMap() {
//        
//        // Clear and create a new map
//        mTileList = new ArrayList();
//        mChunkList = new ArrayList();
//        
//    }
//    
//    /**
//     * Get the buffer for pixel point (x,y). Will generate one if it doesn't exist.
//     * @param x
//     * @param y
//     * @return BufferedImage
//     */
//    protected MapChunk get(int x, int y) {
//        
//        // Figure out which chunk this is in
//        int cx = 0, cy = 0;
//        while(x >= mChunkSize) {
//            cx ++;
//            x -= mChunkSize;
//        }
//        while(y >= mChunkSize) {
//            cy ++;
//            y -= mChunkSize;
//        }
//        
//        // Check to see if this buffer already exists
//        for(MapChunk chunk : mChunkList) {
//            if(chunk.mChunkX == cx && chunk.mChunkY == cy) {
//                return chunk;
//            }
//        }
//        
//        // This chunk hasn't been made yet. So make it
//        Image img = new Image();
//        img.create(mChunkSize, mChunkSize, Color.BLACK);
//        
//        MapChunk chunk = new MapChunk(img, cx, cy);
//        mChunkList.add(chunk);
//        
//        return chunk;
//        
//    }
//    
//    /**
//     * Get chunk size in pixels.
//     * @return 
//     */
//    public int getChunkSize() {
//        return mChunkSize;
//    }
//    
//    public int getMaxPlayers() {
//      return maxPlayers;
//    }
//    
//    public List<Vector2f> getPlayerStart(){
//      return mPlayerStart;
//    }
//    
//    /**
//     * Get map width in chunks.
//     * @return 
//     */
//    public int getWidth() {
//        return (int)mMapSize.x;
//    }
//    
//    /**
//     * Get map height in chunks.
//     * @return 
//     */
//    public int getHeight() {
//        return (int)mMapSize.y;
//    }
//    
//    public MapChunk getChunkAt(int x, int y) {
//        for(MapChunk chunk : mChunkList) {
//            if(chunk.mChunkX == x && chunk.mChunkY == y) {
//                return chunk;
//            }
//        }
//        return null;
//    }
//    
//    /**
//     * Remove the dynamic Image objects which were created in createChunks.
//     */
//    public void cleanup() {
//      mTileList = null;
//      mChunkList = null;
//    }
//    
//    /**
//     * Generate a series of Buffered images which draw the generated tiles from mTileList.
//     */
//    protected void createChunks() {
//        
//        // NOTE: Each BufferedImage will be 256x256
//        
//        // Loop through the tiles
//        for(Tile tile : mTileList) {
//            
//            int tx, ty;
//            tx = (int)tile.getPosition().x;
//            ty = (int)tile.getPosition().y;
//            
//            for(int x = tx; x < tx + mTileSize; x++) {
//                for(int y = ty; y < ty + mTileSize; y++) {
//                    
//                    
//                    
//                    MapChunk thisChunk = get(x, y);
//                    
//                    //System.out.println((x - (thisChunk.mChunkX*mChunkSize)) + "," + (y - (thisChunk.mChunkY*mChunkSize)));
//                    
//                    thisChunk.mImage.setPixel(  x - (thisChunk.mChunkX*mChunkSize),
//                                                y - (thisChunk.mChunkY*mChunkSize),
//                                                tile.getColor());
//                    
//                }
//            }
//            
//        }
//        
//    }
//  
//  
//  
//  //Getters
//  
//  public List<MapChunk> getChunkList(){
//    return mChunkList;
//  }
  
}
