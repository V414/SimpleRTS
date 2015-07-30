/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.maps.tiles;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.pathfinding.Mover;
import com.dinasgames.main.objects.entities.units.infantry.air.AirInfantry;
import com.dinasgames.main.objects.entities.units.infantry.land.LandInfantry;
import com.dinasgames.main.objects.entities.units.infantry.sea.SeaInfantry;
import com.dinasgames.main.objects.entities.units.vehicles.air.AirVehicle;
import com.dinasgames.main.objects.entities.units.vehicles.land.LandVehicle;
import com.dinasgames.main.objects.entities.units.vehicles.sea.SeaVehicle;

/**
 *
 * @author Jack
 */
public class PathTile implements Tile {
  
  protected boolean mBlockAir, mBlockLand, mBlockSea;
  
  public PathTile( boolean blockAir, boolean blockLand, boolean blockSea ) {
    this.mBlockAir = blockAir;
    this.mBlockLand = blockLand;
    this.mBlockSea = blockSea;
  }
  
  @Override
  public Color getColor() {
    return null;
  }

  @Override
  public boolean blockAir() {
    return mBlockAir;
  }

  @Override
  public boolean blockLand() {
    return mBlockLand;
  }

  @Override
  public boolean blockSea() {
    return mBlockSea;
  }
  
  public boolean isBlocking( Mover mover ) {
    
    // Check if this tile blocks land
    if( blockLand() && (mover instanceof LandVehicle || mover instanceof LandInfantry) ) {
      return true;
    }
    
    // Check if this tile blocks land
    if( blockSea() && (mover instanceof SeaVehicle || mover instanceof SeaInfantry) ) {
      return true;
    }
    
    // Check if this tile blocks land
    if( blockAir() && (mover instanceof AirVehicle || mover instanceof AirInfantry) ) {
      return true;
    }
    
    // No blocking rules for this mover
    return false;
    
  }
  
}
