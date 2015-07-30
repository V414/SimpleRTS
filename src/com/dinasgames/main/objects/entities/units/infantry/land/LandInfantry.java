/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.entities.units.infantry.land;

import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.entities.units.infantry.Infantry;
import com.dinasgames.main.scenes.Scene;

/**
 *
 * @author Jack
 */
public class LandInfantry extends Infantry {
  
  protected LandInfantry(Scene scene) {
      super(scene);
  }

  @Override
  public int getTypeID() {
      return super.getTypeID() | GameObjectType.LandInfantry.getID();
  }

  @Override
  public String getTypeString() {
      return "LandInfantry";
  }
  
}
