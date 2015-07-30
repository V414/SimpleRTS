package com.dinasgames.main.maps.tiles;

import com.dinasgames.engine.graphics.Color;

public interface Tile {
  
  /**
   * Return a color that will be used to render this tile. Note: Returning a random value from this function will change the tile color pixel by pixel.
   * @return 
   */
  public Color getColor();
  
  /**
   * Whether to block air units from passing this tile. I.e. a plane
   * @return 
   */
  public boolean blockAir();
  
  /**
   * Whether to block land units from passing this tile. I.e. a tank
   * @return 
   */
  public boolean blockLand();
  
  /**
   * Whether to block sea units from passing this tile. I.e. a boat
   * @return interface
   */
  public boolean blockSea();
  
  /**
   * A string representation of this tile. I.e "Grass"
   * @return 
   */
  public String toString();
  
}