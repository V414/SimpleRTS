/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.maps.tiles;

import com.dinasgames.engine.graphics.Color;

/**
 *
 * @author Jack
 */
public class MountainTile implements Tile {
  
  private static final Color color = new Color(34, 34, 34, 255);
  
  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public boolean blockAir() {
    return true;
  }

  @Override
  public boolean blockLand() {
    return true;
  }

  @Override
  public boolean blockSea() {
    return true;
  }
  
  @Override
  public String toString() {
    return "Mountain";
  }
  
}
