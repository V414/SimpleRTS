package com.dinasgames.main.objects.entities.buildings;

import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.scenes.Scene;

public class Building extends Entity {
  
  protected Building(){
    
  }
  
  protected Building(Scene scene) {
      super(scene);
  }
  
  @Override
  protected void recalculateBoundingBox() {
    super.recalculateBoundingBox();
    mBoundingBox.setSize(mSize);
  }
  
  @Override
  public int getTypeID() {
      return super.getTypeID() | GameObjectType.Building.getID();
  }

  @Override
  public String getTypeString() {
      return "Building";
  }
  
}